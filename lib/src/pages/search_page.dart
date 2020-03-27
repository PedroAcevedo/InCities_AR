import 'package:flutter/material.dart';
import 'dart:io';

class SearchPage extends StatelessWidget {
  const SearchPage({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      child: Scaffold(
        appBar: AppBar(
          title: Text('Search'),
        ),
      body: ListView(
        children: [
          _itemList(context)
        ] 
      ),
      ),
    );
  }

   Widget _itemList(BuildContext context) {
      return Card(
        //clipBehavior: Clip.antiAlias,
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(15.0)),
        child: 
        Column(children: <Widget>[
          ListTile(
            leading: Icon(Icons.image,color: Colors.blue,),
            title: Text("Item 1"),
            subtitle: Text('Prueba Módulo RA'),
            onTap: () {
              if(Platform.isAndroid){
                Navigator.pushNamed(context, 'AndroidRA');
              }else{
                Navigator.pushNamed(context, 'IOSRA');
              }
            },
          )
        ],
        ),);
    }
}