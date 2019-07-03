import os


import mysql.connector
from mysql.connector import errorcode

def put():
    pass

user=""
password=""
host=""
db=""

try: 
    cnx = mysql.connector.connect(
        user=user, 
        password=password,
        host=host,
        database=db
        )
    cursor = cnx.cursor()

    query = ("SELECT name FROM employees "
         "WHERE hire_date BETWEEN %s AND %s")

    cursor.execute(query, (0, 0))
except mysql.connector.Error as err:
    if err.errno == errorcode.ER_ACCESS_DENIED_ERROR:
        print("Something is wrong with your user name or password")
    elif err.errno == errorcode.ER_BAD_DB_ERROR:
        print("Database does not exist")
    else:
        print(err)
else:
    cursor = cnx.cursor()
    cnx.close()      