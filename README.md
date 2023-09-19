# File Management Service

The File Management Service is a web application that provides users with the ability to upload, manage, and download files through a set of RESTful APIs. This service also supports the storage of metadata for each uploaded file, including the file name, creation timestamp, size, file type.

[Watch the Demo Video](https://drive.google.com/file/d/1fW0mI9Nvt2CFRcaX85Eec6zawRp3Rlty/view)

## Features

- **Upload File:** Users can upload files along with metadata. File binaries will be stored in **S3 bucket** anf thier metadata will be stored in **mySql**.

- **Download File:** Users can download a specific file based on its unique identifier(fileId).

- **Update File:** Users can update an existing file or its metadata.

- **Delete File:** Users can delete a specific file based on its unique identifier.

- **List Files:** Users can list all available files and their associated metadata.

## Technologies Used

- **Backend Framework:** Java, Spring Boot Framework, Maven, Prometheus & Micrometer.

- **Database:** mySql for metadata storage

- **File Storage:** Amazon S3 for storing file binaries

- **Frontend:** HTML, CSS, JavaScript

## Setup

1. Clone the repository to your local machine:

   ```
   git clone <repository-url>
   ```

2. Install the necessary dependencies for the backend using Maven:

   ```
   cd backend
   mvn clean install
   ```

3. Configure the database and Amazon S3 storage credentials in the `application.properties` file. Or set mentioned below environment variables, application.properties will load values from environment variable on startup.
 ```
   S3_BUCKET_NAME:"s3 bucket name in which binary files will be uploaded "
   S3_BUCKET_REGION: "region of s3 bucket"
   ACCESS_KEY:"access key of IAM user who has read/write access to the s3 bucket"
   SECRET_KEY:"secret key of the same IAM user."
   MYSQL_URL:"mysql url where metadata will be stored"
   MYSQL_USERNAME:"user name of mysql"
   MYSQL_PASSWORD:"password of user"
 ```

4. Create Mysql table by running below command:  
```
   CREATE TABLE `file_meta_data` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fileId` varchar(255) NOT NULL,
  `fileName` varchar(255) NOT NULL,
  `contentType` varchar(255) NOT NULL,
  `createdAt` datetime NOT NULL,
  `fileSize` bigint NOT NULL,
  `modifiedAt` datetime NOT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `UNIQUE_FILE_ID` (`fileId`);
```
5. Run the backend server:

   ```
   mvn spring-boot:run
   ```

6. Download FileSystemFrontend.zip file present at /file-storage-service/src/main/resources/. Open the frontend by navigating to the  directory where you have unzipped FileSystemFrontend.zip file  and run server

   ```
   cd path to frontend directory
   python3 -m http.server 8090
   ```

7. Now open http.server:8090 in web browser. Assuming you are runnig backend on localhost:8080 You can now use the web UI to interact with the File Management Service without changing domains in script.js
8. You can check backend monitoring metrics of all APIs and musql connections  on this url http://localhost:8080/actuator/prometheus

## API Endpoints

- **Upload  API:**
  - Endpoint: POST /files/upload
  - Input: File binary data, file name, metadata (if any)
  - Output: A unique file identifier
  - Metadata Saved: File name, createdAt timestamp, size, file type
  **Curl**
  ```
   curl --location 'http://localhost:8080/files/upload' \
   --form 'file=@"/Users/satyendra/Downloads/3586_copy_a_276x346_50.jpg"' \
   --form 'fileName="satya"' \
   --form 'metadata="{"fileType":"text/javascript","size":"3866 bytes","createdTime":"17/09/2023, 00:23:14"}"'
  ```
  **Sample succes response**
  ```
  {
    "fileId": "satya-S3-1695065076107",
    "responseInfo": {
        "responseStatus": "SUCCESS",
        "responseCode": "FS0000100",
        "responseMsg": "Request Processed Successfully"
    }
  }
  ```
  **Sample Failure response**
  ```
  {
    "responseStatus": "FAILURE",
    "responseCode": "FS0000300",
    "responseMsg": "Something Went Wrong"
  }
  ```
** Similiar types of response will be retuned in all the api.**
- **Read  API:**
  - Endpoint: GET /files/{fileId}
  - Input: Unique file identifier that ypu recieved in upload.
  - Output: File binary data
  - **Curl**
  ```
   curl --location 'http://localhost:8080/files/satya1694989276234'
  ```

- **Update File API:**
  - Endpoint: PUT /files/{fileId}
  - Input: New file binary data or new metadata
  - Output: Updated metadata or a success message
  - **Curl**
  ```
  curl --location --request PUT 'http://localhost:8080/files/update/sfdsf' \
  --form 'updatedFile=@"/Users/satyendra/Downloads/3586_copy_a_276x346_50.jpg"' \
  --form 'updatedMetadata="{"fileType":"text/javascript","size":"3866 bytes","createdTime":"17/09/2023, 00:23:14"}"' \
  --form 'updatedFileName="Satya"'
  ```
- **Delete File API:**
  - Endpoint: DELETE /files/{fileId}
  - Input: Unique file identifier
  - Output: A success or failure message
  - **Curl**
  ```
   curl --location --request DELETE 'http://localhost:8080/files/screenname-@1694989084792'
  ```

- **List Files API:**
  - Endpoint: GET /files
  - Input: None
  - Output: A list of file metadata objects, including file IDs, names, createdAt timestamps, etc.
  - **Curl**
  ```
  curl --location 'http://localhost:8080/files/list'
  ```

## UI Usage Instructions

1. Upload a File:
   - Use the "File Upload" section in the web UI to upload a file. File name and  metadata will be filled automatically.

2. List Files:
   - The "File List" section displays all available files and their metadata. It get udpated automatically after any write operation.

3. Retrieve File:
   - Click on a file in the list to retrieve it.

4. Update File:
   - Select a file in the list, and then use the "Update File" section to upload a new file or update metadata.

5. Delete File:
   - Select a file in the list and click the "Delete" button to delete it.

## Contributing

Contributions to the File Management Service project are welcome! Please follow these steps to contribute:

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Commit your changes.
4. Push your branch to your fork.
5. Submit a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

Feel free to modify and expand this README to provide more details about your specific project.
