# Changelog
All changes made to this project will be documented in this file

## [RELEASED]
---------------------------------------------------------
## [0.2.1] - 2021-03-21
### Changed
- Small UI tweaks for Managers
- Fixed bug in Comments section when deleting comment with 
a parent
- Fixed bug of task scheduler
---------------------------------------------------------
## [0.2.1] - 2021-03-12
### Changed
- Finished comment section

### Bugs
- Can't delete child comments
---------------------------------------------------------
## [0.2.1] - 2021-03-11
### Changed
- Fixed bug in JPA
- Comment addition and likes working

---------------------------------------------------------
## [0.2.1] - 2021-03-09
### Added
- New frontend design for comments section
- New Comment responding method added

### Changed
- Fixed Backend bugs in Comments feature

---------------------------------------------------------
## [0.2.1] - 2021-03-07
### Added
- Added Backend updates to store comments
- Started integrating pusher into backend

### Changed
- Updated Frontend to incorporate new Backend changes
---------------------------------------------------------
## [0.2.1] - 2021-03-04
### Added
- Started working on article comment section

---------------------------------------------------------
## [0.2.1] - 2021-03-02
### Changed
- Added syntax highlighting
- Fixed visual bug in team member page

---------------------------------------------------------
## [0.2.1] - 2021-02-26
### Added
- New update user feature to user page
- Integrated new feature from DiogoRasteiro

### Changed
- Updated login page and register page interface
- Fixed minor interface bugs

---------------------------------------------------------
## [0.2.1] - 2021-02-25
### Added
- New update article feature
- Started planning website transitions

### Changed
- Code cleanup in authentication service

---------------------------------------------------------
## [0.2.1] - 2021-02-23
### Changed
- Fixed bug when creating and deleting articles
- Users are now redirected to homepage after logout

---------------------------------------------------------
## [0.2.1] - 2021-02-22
### Changed
- Fixed minor bug in firebase tokens

---------------------------------------------------------
## [0.2.1] - 2021-02-21
### Added
- New user page

### Changed
- Corrected Apache refresh error
- Fixed interface bugs
- New backend interface for changing users

---------------------------------------------------------
## [0.2.1] - 2021-02-20
### Added
- Properties for security
- Release info

### Changed
- Minor changes in Contributions Service

---------------------------------------------------------
## [0.2.1] - 2021-02-11
### Added
- New deployment script
- Preparation for test deployment
- Scroll up function for errors
- Integrated TeamMemberFeature into application

### Changed
- Updated README with new project instructions
- Fixed submit article form bugs

---------------------------------------------------------
## [0.2.1] - 2021-02-10
### Added
- New feature to get info from Minecraft API

### Changed
- Users cannot submit empty feedbacks anymore

---------------------------------------------------------
## [0.2.1] - 2021-02-09
### Added
- New register feature for users

### Changed
- Fixed Articles api
- Changed to new Angular version

---------------------------------------------------------
## [0.2.1] - 2021-02-08
### Added
- New global error handling
- New global Http error handling
- New footer for website

### Changed
- Theme of somnus website

---------------------------------------------------------
## [0.2.1] - 2021-02-07
### Added
- New search feature added to search for articles on 
frontend and backend
- New footer added for website
- New value for OAuth in application.properties

### Changed
- Fixed token passing to backend
- Tested angular interface with backend

---------------------------------------------------------
## [0.2.1] - 2021-02-06
### Changed
- Firebase authentication classes
- Started addapting frontend to new backend

---------------------------------------------------------
## [0.2.1] - 2021-02-04
### Added
- New Method to authenticate user through Firebase token
- Started to work on JWT tokens

### Changed
- Field password in user no longer used

---------------------------------------------------------
## [0.2.1] - 2021-02-03
### Changed
- Changed some Firebase configuration files

---------------------------------------------------------
## [0.2.1] - 2021-02-02
### Added
- New Email sending service through spring
- New User authentication method through Firebase
- New User API (still needs adaptation to Firebase login)
- TeamMember stores number of contributions (performance)

### Fixed
- Bug in SomnusExceptionDto: parsing of LocalDateTime

---------------------------------------------------------
## [0.2.1] - 2021-02-01
### Added
- New Feedback API
- New SystemMonitor API
- Started preparing for mailSender
- New develop and production environments

### Changed
- Now using JPA with mysql to store data
- properties files changed to support new database changes
---------------------------------------------------------

## [0.2.0] - 2021-01-31
### Added
- New TeamMember API
- New WebConfig and WebSecurityConfig classes
- Started preparing new User API

### Changed
- Changed to more generic Database connection method

---------------------------------------------------------

## [0.2.0] - 2021-01-30
### Added
- New Database connection method
- New Article database connection

### Changed
- Finished Articles API
- Fixed application properties database connection strings

---------------------------------------------------------

## [0.2.0] - 2021-01-29
### Added
- Start of new java backend for Somnus website
- Custom Exception handler
- Start of Articles API

---------------------------------------------------------

## [0.1.3] - 2020-07-03
### Added
- Gaming list page
- Gaming story page
- Create gaming article page

### Changed
- Interface reviewed
- Bootstrap errors where fixed
- Unnecessary files where deleted from backend

---------------------------------------------------------

## [0.1.3] - 2020-07-02
### Added
- Added new branch for working on firebase integration for backend
- Started creating new classes for firebase database usage
  (on branch mig-firebase-database-swap)

### Changed
- Interface changes where made for deployment
- Started preparing smaller screen app integration

---------------------------------------------------------

## [0.1.3] - 2020-07-31
### Added
- Quill text Editor integration for adding articles
- Created new router structure in frontend
- Created routers in backend
- Created controllers in backend

---------------------------------------------------------

## [0.1.3] - 2020-07-30
### Added
- Massive Interface change
- Started creating new page for articles
- Created writing animation for home page

### Changed
- Redesigned some pages interfaces

---------------------------------------------------------

## [0.1.2] - 2020-07-29
### Added
- New page to create articles
- Preparing backend to store articles in database

### Changed
- Deleted not used files in backend
- Changed app.js to use firebase

---------------------------------------------------------

## [0.1.1] - 2020-07-10
### Added
- Google Login feature with firebase
- Added login button in navbar
