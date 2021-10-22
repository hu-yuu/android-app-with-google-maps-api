import pymongo
import json
import datetime

def hello_world(request):
    request_args = request.args
    request_json = request.get_json()
    if request_args  and 'sorgu13' in request_args:
        return sorgu1()
    elif request_args and 'sorgu23' in request_args:
        return sorgu2(request_args['day1'], request_args['day2'])
    elif request_args and 'sorgu31' in request_args:
        return sorgu3(request_args['day'])
    else :
        return "Hata"








def sorgu1():
    pw = "mongodbpw??"
    db = pymongo.MongoClient("mongodb+mongoclientaddress".format(pw))
    dbyzlb = db['yazlab2']
    coll = dbyzlb['tripdata']
    sorgu13 = coll.find({}, {'tpep_pickup_datetime':1,'tpep_dropoff_datetime':1 , 'trip_distance':1, '_id':0}).sort('trip_distance',-1).limit(5)
    resList = []
    for i in sorgu13:
        dateTimePickUp = str(i['tpep_pickup_datetime'].year) + '-' + str(i['tpep_pickup_datetime'].month) + '-' + str(i['tpep_pickup_datetime'].day)
        dateTimeDropOff = str(i['tpep_dropoff_datetime'].year) + '-' + str(i['tpep_dropoff_datetime'].month) + '-' + str(i['tpep_dropoff_datetime'].day)


        jdict = {'tpep_pickup_datetime': dateTimePickUp,'tpep_dropoff_datetime':dateTimeDropOff , 'trip_distance': i['trip_distance']}
        resList.append(jdict)


    return json.dumps(resList)

def sorgu2(day1, day2):

    pw = "mongodbpw?"
    db = pymongo.MongoClient("mongodb+mongoclientaddress".format(pw))
    dbyzlb = db['yazlab2']
    coll = dbyzlb['tripdata']
    dateTime1 = datetime.datetime(2020, 12, int(day1))
    dateTime2 = datetime.datetime(2020, 12, int(day2))

    sorgu23 = coll.find({'tpep_pickup_datetime':{'$gte':dateTime1, '$lt':dateTime2}
    }).sort('trip_distance',1).limit(5)

    
    resList = []
    for i in sorgu23:
        dateTimePickUp = str(i['tpep_pickup_datetime'].year) + '-' + str(i['tpep_pickup_datetime'].month) + '-' + str(i['tpep_pickup_datetime'].day)
        dateTimeDropOff = str(i['tpep_dropoff_datetime'].year) + '-' + str(i['tpep_dropoff_datetime'].month) + '-' + str(i['tpep_dropoff_datetime'].day)


        jdict = {'tpep_pickup_datetime': dateTimePickUp,'tpep_dropoff_datetime':dateTimeDropOff , 'trip_distance': i['trip_distance'],
        'passanger_count': i['passenger_count'], 'PULocationID': i['PULocationID'], 'DOLocationID': i['DOLocationID']
        }
        resList.append(jdict)
      
    return json.dumps(resList)



def sorgu3(day):
    pw = "mongodbpw"
    db = pymongo.MongoClient("mongodb+mongoclientaddress".format(pw))
    dbyzlb = db['yazlab2']
    coll = dbyzlb['tripdata']
    dateTime = datetime.datetime(2020, 12, int(day))
    dateTime2 = datetime.datetime(2020, 12, int(day)+1)

    sorgu31 = coll.aggregate([
    {
        '$match': {
            'tpep_pickup_datetime': {
                '$gte': dateTime, 
                '$lt': dateTime2
            }
        }
    }, {
        '$sort': {
            'trip_distance': -1
        }
    }, {
        '$limit': 1
    }, {
        '$lookup': {
            'from': 'locationdata', 
            'localField': 'PULocationID', 
            'foreignField': 'LocationID', 
            'as': 'startingLoc'
        }
    }, {
        '$lookup': {
            'from': 'locationdata', 
            'localField': 'DOLocationID', 
            'foreignField': 'LocationID', 
            'as': 'endingLoc'
        }
    }, {
        '$project': {
            'startingLoc._id': 0, 
            'endingLoc._id': 0
        }
    }
    ])

    resList = []
    for i in sorgu31:
        dateTimePickUp = str(i['tpep_pickup_datetime'].year) + '-' + str(i['tpep_pickup_datetime'].month) + '-' + str(i['tpep_pickup_datetime'].day)
        dateTimeDropOff = str(i['tpep_dropoff_datetime'].year) + '-' + str(i['tpep_dropoff_datetime'].month) + '-' + str(i['tpep_dropoff_datetime'].day)


        jdict = {'tpep_pickup_datetime': dateTimePickUp,'tpep_dropoff_datetime':dateTimeDropOff , 'trip_distance': i['trip_distance'],
        'passanger_count': i['passenger_count'], 'PULocationID': i['PULocationID'], 'DOLocationID': i['DOLocationID'],
        'StartingLocation':i['startingLoc'], 'EndingLocation':i['endingLoc']
        }
        resList.append(jdict)

    return json.dumps(resList)