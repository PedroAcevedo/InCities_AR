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
        body: SafeArea(
          child: Stack(children: <Widget>[
            Container(
              padding: EdgeInsets.only(top: 20.0),
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
                      fontSize: 50.0, color: Color.fromRGBO(0, 102, 201, 1)	)),
              Expanded(child: _getDocuments(context))
            ])),
          ]),
        ),
      ),
    );
  }

  Widget _getDocuments(BuildContext context) {
    return FutureBuilder(
      future: menuProvider.loadData(Uri.http(
          "apiavas.dcm-system.co", //'apiavas.dcm-system.co
          "/public/api/RAmodules")),
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
            padding: EdgeInsets.only(
                left: 15.0, right: 15.0, top: 20.0, bottom: 20.0),
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
          ClipRRect(
            borderRadius: BorderRadius.circular(20.0),
            child: Container(
              color: Colors.white,
              margin: EdgeInsets.only( bottom: 15.0 ),
              padding: EdgeInsets.only( bottom: 15.0),
              child: ListTile(
                  leading:
                      Image.asset('assets/ic_${cat["name"].split(" ")[0]}.png'),
                  title: Padding(
                    padding: EdgeInsets.only( bottom: 5.0),
                    child: Text(cat['name'], style: TextStyle(fontWeight: FontWeight.w600),),
                  ),
                  subtitle: Text(cat['description'].split('.')[0] + '.  '),
                  trailing: Icon(Icons.arrow_forward_ios, size: 15.0,),
                  isThreeLine: true,
                  onTap: () {
                    showSearch(
                        context: context,
                        delegate: SearchDelegatePage(cat['id']),
                        query: '');
                    //Navigator.pushNamed(context, 'search');
                  }),
            ),
          ),
        ],
      );
    }).toList();
  }
}

class PermissionsProvider {}
