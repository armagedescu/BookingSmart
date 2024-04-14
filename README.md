# BookingSmart
This is a QA team frontend for booking smartphones, as described in specification

# Prerequisites
1. MySQL database is used
2. scripts/dbcreate.sql used to create the database.
3. Following Hibernate initialization properties might be required to update
     In following two files
        hibernate.create.properties
        hibernate.load.properties
     if database is not local, any nondefault options are used or if scripts/dbcreate.sql was changed:
        hibernate.connection.url=jdbc:mysql://localhost/smartbook
        hibernate.connection.username=smart
        hibernate.connection.password=smt
4.  Program is developed in IntelliJ IDEA under Java21, using maven.
       At best is to load the project under the same IDE and JDK, build and run.

# Initialize database first.
1. MySQL script scripts/dbcreate.sql under a privileged user in MySQL.
     scripts/dbcreate.sql to be changed if needed, but not needed.
     The script refers local database, this attribute can be changed.
     Database smartbook will be created as result.
2. First run to generate data
     Use command line parameter "generate" to run first time, to generate data:
     java org.filipski.Main generate
# Further run, use the existing data
Use command line parameter to run first time, on generated data:
java org.filipski.Main

# If tests required to run
Skip to next secrion if no tests required to be executed.
For running tests refer README.test.md


