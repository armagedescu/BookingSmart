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
2. Note: This method will rebuild each time all the data from scratch. All previous data will be erased.
   First run to generate data. Use command line parameter "generate" to run first time, to generate data:
       java org.filipski.Main generate
# Further run, use the existing data
Use command line parameter to run any time, on generated data:
       java org.filipski.Main

# If tests required to run
Skip to next section if no tests required to be executed.
For running tests refer README.test.md


# Login
Login meant to work as a team member. In this application not intended to be secure.
The Login is meant to enable tester specific functionalities.


# Data Model
Each device has a Model. In application could be many devices of same model. The registry of models is named ModelRegistry.
Each device can zero or multiple schedules. A schedule has a start date and an end date.
Each schedule can be unassigned or assigned to some Tester. A Tester can book any of unassigned schedules.
A tester can book a non existing schedule in any non scheduled time for a given device.
When a tester finishes testing, should write a review. The review will be associaded with the model of the device, but not with the device.

# Reference Date
Program runs with a reference date, instead of today date. This date can be any date in the future or in the passt.
This is done only for the purpose of independence of today date, which changes each day.
It has no practical meaning, but has meaning for being in accordance with pregenerated. 
To run with real today date, run application with command line parameter "resetdate".
   Or in Main.runDefault() uncomment following line: //Model.getModel().resetReferenceDate();
                                      and recompile.
