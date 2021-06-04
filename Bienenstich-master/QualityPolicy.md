### Quality Policy
#### Master Branch
- protected
- git master will approve merges to master

#### Dev Branch
- contains User Story Branches
- always have a working version

#### User Story Branch
- one branch for each user story
- named US# (# is the number of the user story in the backlog)

#### User Story Sub Branches
- not required
- named US#-Task#
- task to be small enough to finish in 2-4 hours

#### Commits
- follow commit guidelines listed below
- state US# and Task# and what was done
- group commit what should be together
- little changes get commits too


#### Commit Guidelines
1. Separate subject from body with a blank line
2. Limit the subject line to 50 characters
3. Capitalize the first character of the subject line
4. Do not end the subject line with a period
5. Use the imperative mood in the subject line
6. Wrap the body at 72 characters
7. Use the body to explain what and why vs. how
source:
“The seven rules of a great Git commit message”
https://chris.beams.io/posts/git-commit/

#### Pull Request Reviews
- be detailed in reviews

**GitHub Workflow**
- create user story branches from dev
- modify locally
- add and commit to user story Branch(do often)
- when user story is done, merge dev into user story branch
- then test
- if it is good, pull request for merge to dev and request a review
- update group that dev has been changed
- merge dev into user story branches when it has been changed
- test dev
- pull request for merge to master
- all members should write a review
- git master will merge the pull request into master

**Unit Tests Blackbox/Whitebox**
  - Unit tests should be written for all new/modified, non-GUI code
  - If you are submitting a pull request, you should have also written unit tests for that code (GUI excluded)
  - Team members are welcome to create additional unit tests for another members code when reviewing pull requests
  - Team members can also write additional unit tests for the given Memoranda code base if they choose
  - Code coverage for the tested class should be at 90%+
  - Unit tests can be constructed as Whitebox or Blackbox: it is up to the person writing the tests
  - All unit tests should be a part of src.test.java

**Code Review** (due Feb 25th)
   - Follow the coding standards provided in the course (Assignment 3: Coding Standards document)

  > Include a checklist/questions list which every developer will need to fill out/answer when creating a Pull Request to the Dev branch.
  - 1. Does my code compile/run properly?
  - 2. Does my code contain the required comments?
  - 3. Are all constants, variables, enums, classes, methods, etc named correctly according to the coding standards?
  - 4. Is my code stylistically consistent with the coding standards?
  - 5. Are my class methods and attributes in the correct order, and are all member variables private?
  - 6. Did you write any unit tests for this code?
  - 7. Can this code potentially break other areas of the software?

  > Include a checklist/question list which every reviewer will need to fill out/anser when conducting a review, this checklist (and the answers of course) need to be put into the Pull Request review.
  - 1. Are there any defects?
  - 2. Are there any code smells?
  - 3. Are any coding standards not followed?
  - 4. Are there any other concerns/comments/issues with the reviewed code?
  - If the answer to any of the above is yes, the reviewer should fill out the Review Form given in Assignment 3

**Static Analysis**  (due start Sprint 3)
  - For new code/code that we are heavily working with we should have no bugs in SpotBugs
  - For CheckStyle, we should try to achieve a goal of 80% less style violations in our code
  - We will be using the CheckStyle xml file provided in class.   

**Continuous Integration**  (due start Sprint 3)
  - All pull requests should pass Travis-CI tests before being merged into dev.
