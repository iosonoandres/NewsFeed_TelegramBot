# news_feed Telegram Bot

enjoy

## Accessing the System

To access the system, you'll need the following login credentials:

**FX Admin:**
- Username: @Andres
- Password: 123ADMIN

**User Bot:**
- To register an account, you'll need to use the ChatID management system.
- For Telegram access, please use the following credentials:
  - Username: Andres
  - Password: 123

## Link to the JAR File

To download the system's JAR file, simply click on the following link:
[Download JAR File](https://drive.google.com/drive/folders/1HKGzjKps_NIzUE0l1ewdBG6so4Fp6wf-?usp=sharing)

## Key Features

At the heart of the bot lies the `myBot` class, which encompasses a range of powerful features:

- **Login System:** Managed by `UserTemporary`, an adaptable object used exclusively during the registration phase.
- **Advanced Verification:** Ensures user authenticity through dynamically generated JSON databases.
- **Button Insertion:** Special methods streamline button insertion and callback handling, eliminating redundancy.
- **Main Node Management:** Includes features such as comment addition, voting, and the display of global user comments and average votes.
- **Spam Prevention:** Robust measures are in place to prevent redundant votes and spam.
- **Dynamic Feed:** Interacts seamlessly with the dynamic feed, offering users a continually evolving source of information.

## FX Graphical Interface

The FX system, known as the "FireNews Panel," is responsible for managing users, feeds, news, and comments. Its interface boasts the following:

- **Visual Feedback:** Visual indicators promptly highlight errors such as empty fields or invalid data.
- **Auto-Save:** Each screen includes an automatic saving mechanism that securely overwrites the database.
- **Alert Pop-ups:** Alerts are triggered if you attempt to navigate away from a screen with unsaved changes.
- **Custom Styling:** Elements are expertly styled using `application.css` to provide a cohesive and visually pleasing user experience.

## User and Feed Management

- **User Management:** A TreeView featuring TreeCell implementation provides an organized overview of all currently logged-in users. By clicking on a user, you can easily modify their username and password.
- **Feed Management:** Users can expand categories and effortlessly add new feeds using the convenient ContextMenu. Items can be edited with a simple click, allowing for text adjustments. Deletion of feeds is also straightforward; enter the link to be removed in the provided window, and save your progress.

## Usage Without a Database

In the event that the database is unavailable, data will be dynamically generated in the local folder after bot usage. Rest assured, the panel will operate smoothly without errors.
