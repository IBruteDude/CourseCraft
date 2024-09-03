
# Course Craft

An Online learning platform

## Run Locally (Requires MYSQL installed)

* #### Clone the project

```bash
 git clone https://github.com/IBruteDude/CourseCraft_Spring-MVC-Project.git
```

* #### Go to the project directory

```bash
 cd CourseCraft_Spring-MVC-Project/
```

* #### Create CourseCraft user and database (Ensure MySQL service is running)

* #### For Windows

    ```powershell
     type src\main\resources\create_user.sql | mysql -uroot -p
    ```

* #### For Linux/WSL

    ```bash
     cat src/main/resources/create_user.sql | mysql -uroot -p
    ```

* #### Install dependencies and Start the web server

* #### For Windows

    ```powershell
     mvnw.cmd clean spring-boot:run
    ```

* #### For Linux/WSL

    ```bash
     ./mvnw clean spring-boot:run
    ```

## Swagger API documentation

```http
 http://localhost:8080/swagger-ui/index.html?url=/v2/api-docs#/
```

## API Specfication (Partially implemented)

### HTML Page views

* #### Get course page

```http
 GET /courses/{courseName}
```

* #### Get user's courses page

```http
 GET /user/courses
```

* #### Get user's cart page

```http
 GET /user/cart
```

* #### Get a new course creation page

```http
 GET /user/courses/create
```

* #### Get a course editor page

```http
 GET /user/courses/{courseName}/edit
```

### JSON REST endpoints

* #### Create a new course

```http
 POST /api/v1/user/courses/create
```

* #### Search course titles

```http
 GET /api/v1/courses/search
```

* ##### JSON parameters

| Parameter     | Type     | Description                           |
| :------------ | :------- | :------------------------------------ |
| `searchQuery` | `string` | **Required**. search string parameter |

* #### Get course recommendations

```http
 GET /api/v1/courses/recommendations
```

* ##### JSON parameters

| Parameter | Type     | Description                        |
| :-------- | :------- | :--------------------------------- |
| `filters` | `object` | **Optional**. user-applied filters |
| `sortBy`  | `string` | **Optional**. sorting parameter    |

* #### Place a new order on the students's cart

```http
 POST /api/v1/courses/{courseId}/cart
```

* #### Make payment request

```http
 POST /api/v1/courses/{courseId}/cart/checkout
```

* #### Reject a pending course

```http
 POST /api/v1/courses/pending/{courseId}/reject
```

* #### Accept a pending course

```http
 POST /api/v1/courses/pending/{courseId}/accept
```

* #### Save a course in user's wishlist

```http
 GET /api/v1/courses/{courseId}/save
```

* #### Delete a course

```http
 DELETE /api/v1/courses/{courseId}
```

* #### Update user information

```http
 PUT /api/v1/user/profile
```

* #### Create a new course module

```http
 POST /api/v1/user/courses/{courseId}/modules/
```

* ##### JSON parameters

| Parameter     | Type     | Description                           |
| :------------ | :------- | :------------------------------------ |
| `title` | `string` | **Required** title of the module to be created. |

* #### Delete a module

```http
 DELETE /api/v1/user/courses/{courseId}/modules/{moduleId}
```

* #### Create a new module lesson

```http
 POST /api/v1/user/courses/{courseId}/modules/{moduleId}/lessons
```

* ##### JSON parameters

| Parameter     | Type     | Description                           |
| :------------ | :------- | :------------------------------------ |
| `title` | `string` | **Required** title of the lesson to be created. |

* #### Update lesson's content

```http
 PUT /api/v1/user/courses/{courseId}/modules/{moduleId}/lessons/{lessonId}
```

* ##### JSON parameters

| Parameter        | Type     | Description                       |
| :--------------- | :------- | :-------------------------------- |
| <lesson object>  | _ |       _ |

* #### Delete a lesson

```http
 DELETE /api/v1/user/courses/{courseId}/modules/{moduleId}/lessons/{lessonId}
```
