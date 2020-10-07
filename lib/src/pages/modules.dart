import 'package:flutter/material.dart';
import 'package:flutter_svg/svg.dart';
import 'package:incities_ar/src/providers/category_provider.dart';
import 'package:incities_ar/src/search/search_delegate.dart';
import 'package:incities_ar/src/providers/permission_provider.dart';

class ModulePage extends StatefulWidget {
  @override
  _ModulePageState createState() => _ModulePageState();
}

class _ModulePageState extends State<ModulePage> {
  bool _isLoading = true;
  @override
  void initState() {
    // TODO: implement initState

    super.initState();
     PermissionsService().requestPermission(onPermissionDenied: () {
        print('Permission has been denied');
      });
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      child: Scaffold(
        body: Stack(children: <Widget>[
          Container(
            margin: EdgeInsets.only(top: 300),
            width: double.infinity,
            height: double.infinity,
            child: SvgPicture.asset(
              'assets/nubes.svg',
            ),
          ),
          Container(
              child: Column(children: <Widget>[
            Align(
              alignment: Alignment.centerLeft,
              child: GestureDetector(
                onTap: () {},
                child: Container(
                  margin: EdgeInsets.only(left: 20, right: 20),
                  height: 140,
                  child: SvgPicture.asset(
                    'assets/logo_incities.svg',
                  ),
                ),
              ),
            ),
            Text('Módulos',
                style: TextStyle(
                    fontSize: 50.0, color: Color(Colors.black.value))),
            Expanded(child: _getDocuments(context))
          ])),
        ]),
      ),
    );
  }

  Widget _getDocuments(BuildContext context) {
    return FutureBuilder(
      future: menuProvider.loadData(Uri.http("10.0.2.2:8000",//'apiavas.dcm-system.co
          "/api/RAmodules")),
      initialData: [],
      builder: (context, AsyncSnapshot<List<dynamic>> snapshot) {
        //print(snapshot.data);
        if (snapshot.connectionState == ConnectionState.waiting) {
          return new Center(
            child: new CircularProgressIndicator(),
          );
        } else if (snapshot.hasError) {
          return new Text('Error: ${snapshot.error}');
        } else {
          return ListView(
            padding: EdgeInsets.only(left: 30.0, right: 30.0, top: 20.0, bottom: 20.0),
            children: _listItems(snapshot.data, context),
          );
        }
      },
    );
  }

  List<Widget> _listItems(List<dynamic> data, BuildContext context) {
    return data.map((cat) {
      return Column(
        children: <Widget>[
          Card(
            child: ListTile(
                leading: Image.asset('assets/ic_${cat["name"].split(" ")[0]}.png'),
                title: Text(cat['name']),
                subtitle: Text(cat['description'].split('.')[0] + '.  '),
                trailing: Icon(Icons.arrow_forward_ios),
                isThreeLine: true,
                onTap: () {
                  showSearch(
                      context: context,
                      delegate: SearchDelegatePage(cat['id']),
                      query: '');
                  //Navigator.pushNamed(context, 'search');
                }),
          ),
        ],
      );
    }).toList();
  }
}

class PermissionsProvider {}
