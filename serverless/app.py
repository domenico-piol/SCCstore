import os
import psycopg2

from os import environ
from flask import Flask, request

app = Flask(__name__)
app.config['SCC_DB_HOST'] = environ.get('SCC_DB_HOST')


@app.route('/')
def hello_world():    
    conn = psycopg2.connect(
        #host="win10-postgresql",
        host=app.config['SCC_DB_HOST'],
        database="sccstore",
        user="sccstore",
        password="sccstore")
        
    cur = conn.cursor()

    cur.execute('SELECT * FROM complaints;')
    dataarray = cur.fetchall()

    cur.close()
    conn.close()

    htmlPageHeader = "<!DOCTYPE html><html><body><h1>Our complaints - delivered by a serverless function</h1>"
    htmlPageContent = ""
    htmlPageFooter = "</body></html>"

    for row in dataarray:
        htmlPageContent += "<p>" + str(row[0]) +" - " + row[1] + "</p>"

    return htmlPageHeader + htmlPageContent + htmlPageFooter

@app.route('/complaint', methods=['POST'])
def write_complaint():
    complaint_value = str(request.form['compl'])

    conn = psycopg2.connect(
        #host="win10-postgresql",
        host=app.config['SCC_DB_HOST'],
        database="sccstore",
        user="sccstore",
        password="sccstore")
        
    cur = conn.cursor()

    cur.execute("INSERT INTO complaints (complaint) VALUES (%s);", (complaint_value,))
    conn.commit()

    cur.close()
    conn.close()

    return complaint_value, 200


if __name__ == "__main__":
    app.run(debug=True,host='0.0.0.0',port=int(os.environ.get('PORT', 8080)))
