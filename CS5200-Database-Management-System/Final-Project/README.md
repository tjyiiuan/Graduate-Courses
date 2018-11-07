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
* Course
* Discussion
* Reply

The institution can be regarded as the main forum, which contains a number of deparments as subforums.
Each deparment may have several courses as topics.
Within a course, new discussions can be started as threads, and can be replied to.

### User Groups
We have 4 kinds of potential users, plus system Admin.
* Student
* TA
* Professor
* Department Manager
* Admin

As the basic registered user, student may enroll in any course from its department as instructed.
They can start new discussion under each course, and can give reply to any discussion.
Student can share their ideas and get possible answers via discussion with each other. 
Users may also follow other users and get informed of the latest updates from the followees.

Instructors, i.e. professor and TA, are the moderators of the course.
Besides the basic privilege as basic user, they are granted access to the threads of all students for the purpose of moderating discussion.
Instructors can delete/merge discussions, provide official solution to student's doubts.
TAs are also students but are promoted within specific courses.

Department managers take control of courses under department. 
They may add/delete courses, promote/demote users.
They can monitor the whole teaching process, modify courses offered by department, and moderate instructors of a course.

Administrator can administer all types of users, all domain objects, and all relationships in between.

We assign limited access to anonymous visitors. 
They can only view the department or course list, but are restricted from discussions and replies.

### API Usage
User may search for specific discussions with key words. 
A summary of the search results will return and user can navigate to a detail page.
We embed [Google Custom Search Engine][cse](CSE) into the system to accomplish such goal.
CSE enables admins to create a search engine for the entire system, and to use their expertise to narrow users' searching results.
CSE uses search features such as refinements, autocomplete, and promotions to enhance users' search experience.
With CSE API, students can search discussions of their interests, and join discussion efficiently with no need of scrolling down and looking for topics one by one.
Moderators can also use searching features to avoid improper discussions.

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
Navigate to http://localhost:5000/ .



[cse]: https://developers.google.com/custom-search/