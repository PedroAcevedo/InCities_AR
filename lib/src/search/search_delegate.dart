import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:incities_ar/src/models/augmented_files_model.dart';
import 'package:incities_ar/src/providers/augmented_provider.dart';

class SearchDelegatePage extends SearchDelegate {

 final augmentedProvider = AugmentedProvider();
  
  @override
  List<Widget> buildActions(BuildContext context) {
   return [
      IconButton(
        icon: Icon( Icons.clear ),
        onPressed: () {
          query = 'hola';
        },
      )
    ];
  }

  @override
  Widget buildLeading(BuildContext context) {
    return IconButton(
      icon: AnimatedIcon(
        icon: AnimatedIcons.menu_arrow,
        progress: transitionAnimation,
      ),
      onPressed: (){
        close( context, null );
      },
    );
  }

  @override
  Widget buildResults(BuildContext context) {
    return Container();
  }

  @override
  Widget buildSuggestions(BuildContext context) {
    // Son las sugerencias que aparecen cuando la persona escribe
    if ( query.isEmpty ) {
      return Container();
    }

    return FutureBuilder(
      future: augmentedProvider.searchBooks(query),
      builder: (BuildContext context, AsyncSnapshot<List<Book>> snapshot) {
          print(snapshot.toString());
          if( snapshot.hasData ) {
            
            final books = snapshot.data;
          
            return ListView(
              children: books.map( (book) {
                  return ListTile(
                    leading: Icon(Icons.data_usage),
                    title: Text( book.name ),
                    onTap: () async {
                      close( context, null);
                      if(Platform.isAndroid){
                          final Directory systemTempDir = Directory.systemTemp;
                          final File tempFile = File('${systemTempDir.path}/image_database.imgdb');
                          // create tempfile
                          await tempFile.create();
                          await rootBundle.load("assets/image_database.imgdb").then((data) {
                            tempFile.writeAsBytesSync(
                                data.buffer.asUint8List(data.offsetInBytes, data.lengthInBytes));
                                Navigator.pushNamed(context, 'AndroidRA');
                            }).catchError((error) {
                            throw Exception(error);
                          });
                      }else{
                        Navigator.pushNamed(context, 'IOSRA');
                      }
                    },
                  );
              }).toList()
            );

          } else {
            return Center(
              child: CircularProgressIndicator()
            );
          }

      },
    );
  }
}