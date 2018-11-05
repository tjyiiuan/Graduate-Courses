## Project Description
Collaborative discussion is of vital importance in achieving critical thinking and helping students learn effectively and efficiently.
Thanks to the Internet we have plenty of great online Q&A platforms for students and their instructors.
But despite their convenience, students may also facing significant privacy concerns from those platforms.
The aim of our project is not only to provide yet another institutional LMS, but also give a possible solution for institutions to protecting student data privacy. 
After all, once the LMS is run and maintained only by institution itself, students are less vulnerable to data breach by third party vendors behind the scenes.

### Domain 
Our project is based on the domain of education institute, such as college and university.
Very similar to that of the discussion forum, our system is hierarchical in structure.
Potential domain objects include:
* Institution
* Department
* Courses
* Discussion

 
### User
We have 4 kinds of potential users, plus system Admin.
* Student
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


## Quickstart
### Virtual Environment
Please make sure that the Python 3 `venv` package is installed. 
If you are using Ubuntu Linux system, you can install it as follows:
```
$ sudo apt-get install python3-venv
```
### Installation
```
$ git clone
$ cd
$ python3 -m venv venv
$ source venv/bin/activate
$ pip install -r requirement.txt
```
### Visit
Navigate to http://localhost:5000/



[cse]: https://developers.google.com/custom-search/