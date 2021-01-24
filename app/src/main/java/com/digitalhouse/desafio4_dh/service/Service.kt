package com.digitalhouse.desafio4_dh.service

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

var db: FirebaseFirestore = FirebaseFirestore.getInstance()
var cr: CollectionReference = db.collection("games")