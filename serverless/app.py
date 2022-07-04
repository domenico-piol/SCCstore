import os
import psycopg2

from flask import Flask

app = Flask(__name__)

@app.route('/')
def hello_world():    
    conn = psycopg2.connect(
        host="win10-postgresql",
        database="sccstore",
        user="sccstore",
        password="sccstore")
        
    cur = conn.cursor()

    cur.execute('SELECT * FROM compliants;')
    dataarray = cur.fetchall()

    cur.close()
    conn.close()

    htmlPageHeader = "<!DOCTYPE html><html><body><h1>Our complains/h1>"
    htmlPageContent = ""
    htmlPageFooter = "</body></html>"

    for row in dataarray:
        htmlPageContent = htmlPageContent + "<p>" + row[0] + "</p"

    return htmlPageHeader + htmlPageContent + htmlPageFooter

    #target = os.environ.get('TARGET', 'World')
    #return 'Hello {}!\n'.format(target)

if __name__ == "__main__":
    app.run(debug=True,host='0.0.0.0',port=int(os.environ.get('PORT', 8080)))
