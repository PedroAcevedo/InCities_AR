import 'package:flutter/material.dart';
import 'package:incities_ar/src/pages/search_page.dart';
import 'package:incities_ar/src/providers/category_provider.dart';
import 'package:incities_ar/src/search/search_delegate.dart';

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
      future: menuProvider.loadData(Uri.https('jsonblob.com', '/api/jsonBlob/30fa0d50-7eb7-11ea-b97d-87eeec80a901')),
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
              showSearch(context: context, delegate: SearchDelegatePage(), query: '');
              //Navigator.pushNamed(context, 'search');
            },
          ),
          Divider()
        ],
      );
    }).toList();
  }
}