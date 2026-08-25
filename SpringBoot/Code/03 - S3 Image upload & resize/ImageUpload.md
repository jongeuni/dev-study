Bucket 생성과 자격 증명 (access key, secret key) 그리고 스프링부트와 S3와의 연동은 되어있어야 한다. yml 파일은 이와 같다.

```yml
cloud:
  aws:
    stack:
      auto: false
    region:
      static: ap-northeast-2
    s3:
      bucket: ${BUCKET:}
    credentials:
      access-key: ${AWS_ACCESS_KEY:}
      secret-key: ${AWS_SECRET_KEY:}

logging.level.com.amazonaws.util.EC2MetadataUtils: error
```

연동시 Amazon 관련 문제가 생긴다면 [참고]( https://lemontia.tistory.com/1006)한다. 

<br>

## S3 Image upload

`@RequestPart` `@RequestParam` 을 이용하면 *multipart/form-data* 요청을 받을 수 있다. 나는 **`@ModelAttribute`**를 이용했다.

```java
@PostMapping
public ResponseEntity<String> projectCreate(@RequestHeader("Authorization") String token, @ModelAttribute ProjectCreateRequest project){
        projectCreateService.createProject(project.getName(), jwtTokenProvider.getEmail(token),project.getImage())
        ...
}
```

file의 타입은 `MultipartFile`이다.

<br>

file upload 관련 클래스는 FileUploadService와 S3Service두 개가 있다. FileUploadService가 S3Service를 의존 주입 해 사용한다.

<br>

```java
@Service
@RequiredArgsConstructor
public class FileUploadService {
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
    
}
```

우선 고유한 파일 이름을 만든다. S3에 들어갈 때 중복되면 안 되기에 UUID를 사용한다. 객체가 UUID 객체이므로 문자열을 얻기 위해 toString()을 사용한다. [concat()](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/String/concat) 메서드는 매개변수로 전달된 문자열을 호출 문자열에 붙인 새로운 문자열을 반환한다. 그러니까 createFileName에 인자로 사용된 file.getContentType()을(확장자명) concat()을 이용해 UUID로 생성된 아이디 뒤에 붙여준다. 그러면 새로운 **fileName**이 생성된다.content type은 같아야 하기 때문이다.

**ObjectMetadata**에 해당 파일에 대한 추가 정보를 담는다. 아마존 S3에 함께 저장될 메타데이터를 나타내는 것 같다. 

`try(InputStream inputStream = file.getInputStream()){...}`은 try-with-resources문이다. try resources문은 각각의 리소스들이 닫힌다는 것을 보장한다. 프로그램이 끝날 때 꼭 close를 사용해 닫혀야 하는 객체에 유용하다. try catch 문에서는 finally에서 close를 해주었었는데 그렇지 않아도 된다.

file.getInputStream은 파일 내용을 읽은 InputStream을 반환한다. InputStream은 자바에서 제공하는 기본 입력 클래스이다. 바이트 데이터를 읽는데 사용한다. 파일로부터 데이터를 읽는데 사용했다고, 파일에 관한 데이터를 **inputStream**에 저장했다고 보면 될 것 같다.

그러면 ***filename***, ***objectMetadatam***, ***inputStream***이 생긴다. 이 세 가지 객체를 S3Service의 uploadFile 메서드에 넘겨준다.

```java
@RequiredArgsConstructor
@Service
public class S3Service {
    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public void uploadFile(InputStream inputStream, ObjectMetadata objectMetadata, String fileName, String folder){
        amazonS3Client.putObject(new PutObjectRequest(bucket+folder, fileName, inputStream, objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public String getFileUrl(String fileName){
        return String.valueOf(amazonS3Client.getUrl(bucket, fileName));
    }
}

```

putObject 메서드를 이용해서 버킷 위치, 파일 명, 파일바이너리 데이터로 PutObjectRequest 객체를 생성한다.

![PutObjectRequest](https://user-images.githubusercontent.com/66874658/131669875-ed180aa7-74f4-4984-9a71-27f9f00cf156.JPG)

이렇게 하면 S3에 파일 업로드가 끝난다. bucketName에 /폴더를 더해주면 폴더가 생성된다.

<br>

## Image resize

이미지 리사이징 방법은 크게 두 가지가 있는 것 같다. 첫 번째 방법은 자바를 사용해 이미지 리사이징 후 저장하는 방법이고 두 번째 방법은 aws lambda를 이용한 리사이징이다. 난 첫 번째 방법을 선택했다.

```java
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
```

[Thumbnailator 의존성](https://mvnrepository.com/artifact/net.coobird/thumbnailator/0.4.1)을 추가했다. Thumbnails은 이미지 용량이 너무 크거나, 썸네일이 필요한 경우 사용할 수 있다.

BufferedImage는 이미지를 설명한다. ImageIO는 ImageReader 및 ImageWriter을 찾고 인코딩 및 디코딩을 수행하기 위한 메서드가 포함된 클래스이다. ImageIO.read()는 제공된 파일을 디코딩한 결과로 BufferedImage를 반환한다. 

Thumbnailator의 size() 메서드는 가로 길이를 기준으로 사이즈를 조절하게 되고, 이미지의 비율은 유지된다. of 뒤에는 사진의 정보를 알 수 있는 것을 넣으면 될 것 같다. url을 넣어도 된다. (하지만 .getContentType을 하기 때문에 MultipartFile을 넣는 게 맞을 것 같았다.)

너무 길고... 그냥 코드가 마음에 안 들어서 나름 연구를 해보았다.

<br>

InputStream으로 가기 위한 과정이 너무 길다고 생각했다. 현재

1. file의 정보를 BufferedImage에 담는다. 

2. 담은 BufferedImage 객체를 이용해 사이즈를 바꾸고 다시 BufferedImage에 담는다.

3. ByteArrayOutputStream로 이미지 파일을 생성한다. 

   imageType.substring(imageType.indexOf("/")+1)은 확장자를 가지고 오는 것이다.

4. ByteArrayOutputStream을 byte 배열로 변환한다. 그래야 new ByteArrayInputStream으로 InputStream을 만들 수 있기 때문이다.

이렇게 이루어지는데, BufferedImage에서 바로 InputStream으로 변환하는 방법은 없나 찾아봤지만 [없는 것 같았다](https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=vphljc&logNo=116544408). [BufferedImage를 MultipartFile로 변환하는 방법](http://ko.uwenku.com/question/p-vaylfssr-oy.html )이 있는데 (FileInputStream으로 바로 InputStream을 얻을 수 있다.) 클래스를 하나 더 만들어야 해서 관두었다.

더 좋은 방법을 찾았으면 좋겠다.

<br>

## Filed...

그래도 나름 끙차 줄여보았는데

```java
public String imageResizeAndUpload02(MultipartFile file, String folder){
        String fileName = createFileName(file.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        try{
            BufferedImage image1 = Thumbnails.of(ImageIO.read(file.getInputStream())).size(100,100).asBufferedImage();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image1, file.getContentType(), baos);
            baos.flush();
            InputStream thumbInput = new ByteArrayInputStream(baos.toByteArray());
            s3Service.uploadFile(thumbInput,objectMetadata, fileName, folder);
            thumbInput.close();
        } catch (Exception e){
            throw new BaseException(ExceptionMessage.FILE_SIZE_CONVERSION_FAIL);
        }
        return s3Service.getFileUrl(fileName);
    }
```

Obyte가 나온다. 조금 더 수정해서

```java
public String imageResizeAndUpload02(MultipartFile file, String folder){
        String fileName = createFileName(file.getOriginalFilename());
        try{
            BufferedImage bufferImage = ImageIO.read(file.getInputStream());
            BufferedImage image1 = Thumbnails.of(bufferImage).size(100,100).asBufferedImage();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            String imageType = getFileExtension(fileName);
            ImageIO.write(image1, imageType.substring(imageType.indexOf(".")), baos);
            baos.flush();

            ObjectMetadata objectMetadata = new ObjectMetadata(); // object 정보
            byte[] thumbBytes = baos.toByteArray();
            objectMetadata.setContentLength(thumbBytes.length);
            objectMetadata.setContentType(file.getContentType());

            InputStream thumbInput = new ByteArrayInputStream(baos.toByteArray());
            s3Service.uploadFile(thumbInput,objectMetadata, fileName, folder);
            thumbInput.close();
            baos.close();
        } catch (Exception e){
            throw new BaseException(ExceptionMessage.FILE_SIZE_CONVERSION_FAIL);
        }
        return s3Service.getFileUrl(fileName);
    }
```

이렇게 만들었는데도... 왜 0byte가 나오는지는 의문이었다.