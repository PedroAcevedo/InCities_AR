import 'package:arcore_flutter_plugin/arcore_flutter_plugin.dart';
import 'package:flutter/material.dart';
import 'package:incities_ar/src/models/augmented_files_model.dart';
import 'package:incities_ar/src/providers/augmented_provider.dart';
import 'package:incities_ar/src/search/search_delegate.dart';

class ARCodePage extends StatefulWidget {
  @override
  _ArCorePageState createState() => _ArCorePageState();
}

class _ArCorePageState extends State<ARCodePage> {
  ArCoreController arCoreController;
  final augmentedProvider = AugmentedProvider();

  String objectSelected;

  @override
  Widget build(BuildContext context) {
    final Map arguments = ModalRoute.of(context).settings.arguments as Map;
    objectSelected = arguments['URL'];
    return Scaffold(
      appBar: AppBar(
        title: const Text('InCities'),
      ),
      drawer:Drawer(
        child: Column(
          children: <Widget>[
             DrawerHeader(
               decoration: BoxDecoration(
                 gradient: LinearGradient(
                   colors: <Color>[
                   Colors.deepOrange,
                   Colors.orangeAccent
                 ]),
               ),
               child: Container(),),
             new Expanded(
               child: _RetrieveOA(context),
             )
          ],)
      ),
      body: ArCoreView(
          onArCoreViewCreated: _onArCoreViewCreated,
          enableTapRecognizer: true,
        ),
    );
  }

  void _onArCoreViewCreated(ArCoreController controller) {
    arCoreController = controller;
    arCoreController.onNodeTap = (name) => onTapHandler(name);
    arCoreController.onPlaneTap = _handleOnPlaneTap;
  }

  void _addToucano(ArCoreHitTestResult plane) {
    final toucanNode = ArCoreReferenceNode(
        name: "Toucano",
        objectUrl: objectSelected,
        position: plane.pose.translation,
        rotation: plane.pose.rotation);

    arCoreController.addArCoreNodeWithAnchor(toucanNode);
  }

  void _handleOnPlaneTap(List<ArCoreHitTestResult> hits) {
    final hit = hits.first;
    _addToucano(hit);
  }

  void onTapHandler(String name) {
    print("Flutter: onNodeTap");
    showDialog<void>(
      context: context,
      builder: (BuildContext context) => AlertDialog(
        content: Row(
          children: <Widget>[
            Text('Remove $name?'),
            IconButton(
                icon: Icon(
                  Icons.delete,
                ),
                onPressed: () {
                  arCoreController.removeNode(nodeName: name);
                  Navigator.pop(context);
                })
          ],
        ),
      ),
    );
  }

  @override
  void dispose() {
    arCoreController.dispose();
    super.dispose();
  }

  Widget _RetrieveOA(BuildContext context) {
    return FutureBuilder(
      future: augmentedProvider.searchBooks(''),
      builder: (BuildContext context, AsyncSnapshot<List<Book>> snapshot) {
        print(snapshot.toString());
        if (snapshot.hasData) {
          final books = snapshot.data;

          return
          ListView(
              children: books.map((book) {
            return ListTile(
              leading: Image(
                image: NetworkImage(book.thumbnail.isEmpty
                    ? "https://www.tibs.org.tw/images/default.jpg"
                    : book.thumbnail),
              ),
              title: Text(book.name),
              subtitle: Text(book.description),
              onTap: () async {
                objectSelected = book.objectUrl;
                Navigator.pop(context);
              },
            );
          }).toList());
        } else {
          return Center(child: CircularProgressIndicator());
        }
      },
    );
  }
}
