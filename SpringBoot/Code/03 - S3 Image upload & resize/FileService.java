package InTechs.InTechs.file;

import InTechs.InTechs.project.value.Image;
import InTechs.InTechs.exception.BaseException;
import InTechs.InTechs.exception.ExceptionMessage;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {
    private final S3Service s3Service;

    public String uploadImage(MultipartFile file, String folder){
        String fileName = createFileName(file.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        try(InputStream inputStream = file.getInputStream()){
            s3Service.uploadFile(inputStream, objectMetadata, fileName, folder);
        } catch(IOException e){
            throw new IllegalArgumentException(String.format("Error during file conversion (%s)", file.getOriginalFilename()));
        }
        return s3Service.getFileUrl(fileName);
    }

    private String createFileName(String originalFileName){
        return UUID.randomUUID().toString().concat(getFileExtension(originalFileName));
    }

    private String getFileExtension(String fileName){
        try{
            return fileName.substring(fileName.lastIndexOf("."));
        } catch(StringIndexOutOfBoundsException e){
            throw new BaseException(ExceptionMessage.INVALID_FILE_TYPE);
        }
    }

    // image resize
    public Image imageResizeAndUpload(MultipartFile file, String folder) {

        String fileName = createFileName(file.getOriginalFilename());

        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){
            BufferedImage bufferImage = ImageIO.read(file.getInputStream());
            BufferedImage bufferImageResize = Thumbnails.of(bufferImage).size(300,300).asBufferedImage();

            String imageType = file.getContentType();
            ImageIO.write(bufferImageResize, imageType.substring(imageType.indexOf("/")+1), outputStream);

            ObjectMetadata objectMetadata = new ObjectMetadata();
            byte[] outputStreamBytes = outputStream.toByteArray();
            objectMetadata.setContentLength(outputStreamBytes.length);
            objectMetadata.setContentType(file.getContentType());

            InputStream thumbInput = new ByteArrayInputStream(outputStreamBytes);
            s3Service.uploadFile(thumbInput, objectMetadata, fileName, folder);

            thumbInput.close();
        } catch(Exception e){
            throw new BaseException(ExceptionMessage.FILE_SIZE_CONVERSION_FAIL);
        }
        return Image.builder()
                .oriName(fileName)
                .imageUrl(s3Service.getFileUrl(fileName))
                .build();
    }
    public void fileDelete(String folder, String oriName){
        s3Service.deleteFile(folder, oriName);
    }
}
