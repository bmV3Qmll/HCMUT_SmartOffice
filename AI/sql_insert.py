# CREATE TABLE RollCall (name TEXT, time DATETIME, stat BOOLEAN NOT NULL, PRIMARY KEY (name(255), time));
# CREATE TABLE present (name TEXT, stat BOOLEAN, PRIMARY KEY (name(255)));
import mysql.connector
from datetime import datetime

mydb = mysql.connector.connect(
    host="localhost",
    user="root",
    password="",
    database="project"
)

mycursor = mydb.cursor()

def query_sql(target):
    mycursor.execute("SELECT stat FROM present WHERE name=%s", (target, ))
    curr_stat = mycursor.fetchone()[0]
    mycursor.execute("INSERT INTO RollCall (name, time, stat) VALUES (%s, %s, %s)", (target, datetime.now().strftime("%Y-%m-%d %H:%M:%S"), not curr_stat))
    mycursor.execute("UPDATE present SET stat=%s WHERE name=%s", (not curr_stat, target))
    mydb.commit()
    print(mycursor.rowcount, "record inserted.")