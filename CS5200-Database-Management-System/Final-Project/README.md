## Project Description
Collaborative discussion is of vital importance in achieving critical thinking and helping students learn effectively and efficiently.
Thanks to the Internet we have plenty of great online Q&A platforms for students and their instructors.
But despite their convenience, students may also facing significant privacy concerns from those platforms.
The aim of our project is not only to provide yet another institutional LMS, but also give a possible solution for institutions to protecting student data privacy. 
After all, once the LMS is run and maintained only by institution itself, students are less vulnerable to data breach by third party vendors behind the scenes.

### Domain 
Our project is based on the domain of education institute, such as college and university. 
Potential domain objects include:
* Institution
* Department
* Courses
 
### User
We have 4 kinds of potential users, plus system Admin.
* Students
* TA
* Professor
* Department Manager
* Admin

We assign limited access to anonymous visitors. 
They can only view the department or course list, but are restricted from posts/replies.

- 3 goals the user could accomplish
- 2 relations user with other users
- 2 relations user with domain objects
- 2 relations object with other domain objects

### API Usage
We embed [Google Custom Search][cse] to increase site search experience of our users.

## Project Design
- A class diagram that captures users, domain objects, and relations between users, between users and domain objects, and between domain objects.

[cse]: https://developers.google.com/custom-search/