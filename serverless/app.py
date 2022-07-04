import os
import psycopg2

from flask import Flask

app = Flask(__name__)

@app.route('/')
def hello_world():
    print("starting...")
    
    conn = psycopg2.connect(
        host="win10-postgresql",
        database="sccstore",
        user="sccstore",
        password="sccstore")
    
    print("Connected to database")
    
    cur = conn.cursor()
    print("got a cursor")

    cur.execute('SELECT * FROM compliants;')
    print('executed select')
    dataarray = cur.fetchall()

    print (dataarray)

    cur.close()
    conn.close()

    #return complaints

    target = os.environ.get('TARGET', 'World')
    return 'Hello {}!\n'.format(target)

if __name__ == "__main__":
    app.run(debug=True,host='0.0.0.0',port=int(os.environ.get('PORT', 8080)))
