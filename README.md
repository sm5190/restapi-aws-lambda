# AWS Lambda Functions for MySQL based Web Application

## Overview
This repository contains a set of AWS Lambda functions developed for a database-backed (MySQL) web-based application. These functions serve as the backend for a set of REST APIs used by the frontend of the application to communicate with the backend. The deployment architecture of the APIs on AWS is illustrated in the diagram provided in the "Deployment Architecture" section.

## Deployment Architecture

![image](https://github.com/sm5190/restapi-aws-lambda/assets/53345331/74ba5174-2798-4c18-bc34-4503b9d91536)


## API Endpoints
The following API endpoints are defined using the AWS Lambda functions:
- **getAllCategory**: Fetch all the categories from the database.
- **getCategoryName**: Given the category id, fetch the name of the category.
- **getCategoryId**: Fetch the ID of the category with the given category name.
- **addCategory**: Add the given category, with category Id and category name, in the database.
- **getAllBook**: Fetch all the books from the database.
- **getBookById**: Fetch the book given the book id.
- **getBookByCategoryId**: Fetch all books of the given category id.
- **getBookByCategoryName**: Fetch all books of the given category name.
- **getRandomBook**: Fetch 5 random books from the database.
- **addBook**: Add the book in the database given the book information.

All lambda functions are accessible through Amazon API Gateway, and all data is returned in JSON format.

## About Database
The schema of the database together with the tables containing some sample records is provided for this project ( see schema.sql and data.sql). Additional records can be added to the tables as needed. For this project MySQL supported by AWS RDS is used.

## Other
- All common dependencies for the lambda functions is deployed as layers. Any library/dependency used by more than one function is added as a layer.
- Appropriate IAM policies and roles must are defined whenever necessary. Triggers have the proper authorization to invoke the lambda function, and the lambda function has same to communicate with other AWS services such as RDS. See the "IAM Policy" file.
- All Lambda functions write appropriate information to the Cloudwatch log.

