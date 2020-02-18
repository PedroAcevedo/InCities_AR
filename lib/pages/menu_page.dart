import 'package:flutter/material.dart';
import 'package:incities_ar/providers/category_provider.dart';

class MenuPage extends StatelessWidget {
  const MenuPage ({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      child: Scaffold(
        appBar: AppBar( 
          title: Text('InCities AR'),
        ),
        body: _getDocuments(context),
      ),
    );
  }

  Widget _getDocuments(BuildContext context) {
    
    return FutureBuilder(
      future: menuProvider.loadData(),
      initialData: [],
      builder: (context, AsyncSnapshot<List<dynamic>> snapshot){
        //print(snapshot.data);
        return ListView(
          children: _listItems(snapshot.data, context),
        );

      },
    );
  }

  List<Widget> _listItems(List<dynamic> data, BuildContext context){
    return data.map( (cat) {
      return Column(
        children: <Widget>[
          ListTile(
            title: Text( cat['texto'] ),
            leading: Icon(Icons.library_books, size: 40.0),
            trailing: Icon(Icons.arrow_forward_ios, color: Colors.blue ,),
            onTap: () {
              Navigator.pushNamed(context, cat['ruta']);
            },
          ),
          Divider()
        ],
      );
    }).toList();
  }
}