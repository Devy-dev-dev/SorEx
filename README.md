# SorEx

## INTRODUCTION
Source code available at: https://github.com/Devy-dev-dev/SorEx
The name is inspired from “Sardex”, a blockchain in Sardinia.

## Why this blockchain was made  
When a student enrolls to a speciality or when graduating, a university must, for each student, send or ask by mail a lot of documents. This process is slow by nature, expensive, and is subject to very variable loss or delays. It appears necessary that a student can attest instantly and without any data lost that it possesses the documents asked (for example, during the 3rd year of a bachelor’s degree in France, a student applied to a 1st year of a master’s degree). On the other hand, the university wants to send in an instant and secure way the diplomas to the student. Since the number of students enrolling to university is increasing, the universities would benefit a lot from a process that simplify and automate the verification of documents.  

Furthermore, universities often ask the same documents every year (example: the JAPD to enroll in a 3rd year of a bachelor’s degree). By using our blockchain, the university can instantly check that the student has those documents, avoiding the student to send it again the nth time.   
When a student applied to a new speciality (example: a 1st year of master’s degree after a 3rd year of bachelor’s degree), the university just needs to compare the diploma recorded in the block of the student.  
Why use a blockchain in this case? Is it not easier to ask directly to other universities the data about the student directly? Is is simpler to do so, but a blockchain has many advantages:  

The university does not need anymore to record the documents in a numerical way, a simple hash is enough. If the university is hacked, the data of the students are protected;  
The student possess the list of all its diploma thanks to the hash of the block it received during its first enrolment;
If a university’s servers are down, another one can confirm the identity and diplomas of the student;  
if a person wants to cheat the system (example : cheat at the exams, try to add diplomas), at the moment this is detected, the university can ban the student and other universities will know this student is banned. Moreover, the student cannot add or edit its diplomas with our architecture, only universities can;  
when unfortunately a student dies, a university can disable its account and protect everyone from impersonation;
the blockchain makes it possible for universities to check instantly that the student has the required documents without having the student to send those documents again;  
When a student applies, the university involved verifies the student diploma and check that this student is not fraudulent by using the hash of the block sent by the student.  
Other administration institutions such as a prefecture can also use the blockchain to update information about the student (example: in our blockchain, the prefecture can update the student id).   

## Features description
  - 3 entities:
    - 2 mains entities: University, Prefecture
    - 1 secondary entity: Student
When the student is enrolled, the server (Sorbonne Université) store the hash of the student data in the blockchain and add a new block based on the information the student gave. The student receives the hash of its block and can use it to confirm its identity. This block contains in plain text (not hashed) the diplomas of the student and if its status isvalid or not. The other data are hashed;
A university can check the status of students (list of diploma and if the student is valid or not);
The prefecture can update the hash of the ID card of a student (in France, an ID card has to be renewed every ten years);
One block stores much information about a student:
  - its ID card (hashed)
  - its JAPD certificate (hashed)
  - its baccalauréat certificate (hashed)
  - its status (boolean, valid or not)
  - its diplomas (not hashed), example:  
      L1, Computer Science, Sorbonne Université, 2017, 12.3  
      L2, Computer Science, Sorbonne Université, 2018, 14.6  
      
there is no spending system since this blockchain is not made for cryptocurrency
