README

    SCHEDULER

    Scheduler is a tool for managing Customers and Schedules within an organization's database.
    It contains the ability to add/modify/delete Customers and Appointments as well as basic
    reporting. The program requires a login with valid User credentials from the connected
    database. The login screen will be displayed in English or French depending on the user's
    current region setting on their computer.

CONTACT

    If you have any suggestions or problems with this tool contact us by submitting an email to:

    Brayden McArthur
    bmcart5@wgu.edu.


AUTHOR

    Brayden McArthur

VERSION

    1.0

DATE

    12/15/2020

IDE

    IntelliJ IDEA Community Edition 2020.1.2, Java SE  14.0.1, JavaFX-SDK-14.0.1

HOW TO RUN

    Upon running the program you will be prompted to login. Use your username and password that is
    stored in your organization's database. After a successful login you will see an alert box that
    will display all meetings starting within 15 minutes, if any. The next screen you see is the
    main menu where you can select "Schedule", "Customers", "Reports", and "Exit".

    SCHEDULE

    The Schedule window displays a table containing all Appointments from the database. Click the "Add"
    button to add a new appointment. To modify an Appointment, first select the desired Appointment,
    then click "Modify" to edit the information. To delete an Appointment, select the desired Appointment
    and click "Delete". Upon successfully deleting an Appointment, a confirmation window will appear to
    confirm the deletion was successful. Click the "Back" button to return to the main window.

    CUSTOMER

    The Customer window displays a table containing all Customers from the database. Click the "Add"
    button to add a new Customer. To modify a Customer, first select the desired Customer, then click
    "Modify" to edit the information. To delete a Customer, select the desired Customer and click "Delete".
    All Appointments associated with the selected Customer will be deleted first due to foreign key
    constraints. Upon successfully deleting a Customer, a confirmation window will appear to confirm the
    deletion was successful. Click the "Back" button to return to the main window.

    REPORTS

    The Report window contains 3 Tabs to view different reports.
    The "Totals" tab displays the total number of appointments by Appointment Type and by the month of the
    Appointment's Start date.
    The "Contact Schedules" tab displays a table containing a list of all Appointments for the selected
    Contact from the dropdown.

    ***The "Duration" tab displays the total number of hours worth of scheduled Appointments that occur within
    the next month.*** (Additional Report for Part A3f)

    EXIT

    Exit terminates the program.