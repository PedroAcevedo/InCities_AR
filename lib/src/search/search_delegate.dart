import 'dart:io';

import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:incities_ar/src/models/augmented_files_model.dart';
import 'package:incities_ar/src/providers/augmented_provider.dart';
import 'package:path_provider/path_provider.dart';

class SearchDelegatePage extends SearchDelegate {
  final augmentedProvider = AugmentedProvider();
  static const platform = const MethodChannel('com.example.incities_ar');
  var dio = Dio();

  Future download2(Dio dio, String url, String savePath) async {
    try {
      Response response = await dio.get(
        url,
        options: Options(
            responseType: ResponseType.bytes,
            followRedirects: false,
            validateStatus: (status) {
              return status < 500;
            }),
      );
      print(response.headers);
      File file = File(savePath);
      var raf = file.openSync(mode: FileMode.write);
      // response.data is List<int> type
      raf.writeFromSync(response.data);
      await raf.close();
      return file.path;
    } catch (e) {
      print(e);
    }
  }

  @override
  List<Widget> buildActions(BuildContext context) {
    return [
      IconButton(
        icon: Icon(Icons.clear),
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
      onPressed: () {
        close(context, null);
      },
    );
  }

  @override
  Widget buildResults(BuildContext context) {
    if (query.isEmpty) {
      return Container();
    }

    return FutureBuilder(
      future: augmentedProvider.searchBooks(query),
      builder: (BuildContext context, AsyncSnapshot<List<Book>> snapshot) {
        print(snapshot.toString());
        if (snapshot.hasData) {
          final books = snapshot.data;
          return ListView(
              children: books.map((book) {
            return ListTile(
              leading: book.extension == "mp4"? Icon(Icons.video_library) : Icon(Icons.image), 
              title: Text(book.name),
              subtitle: Text(book.activity),
              onTap: () async {
                close(context, null);
                  var tempDir = await getApplicationDocumentsDirectory();
                  String fullPath = tempDir.path + "/" + book.name  +  "." + book.extension;
                  print('full path ${fullPath}');

                  download2(dio, book.download , fullPath);
                  if (Platform.isAndroid) {
                    _callRA(fullPath);
                    //Navigator.pushNamed(context, 'AndroidRA', arguments: <String, String>{ "URL" : book.objectUrl});
                  } else {
                    Navigator.pushNamed(context, 'IOSRA');
                  }
              },
            );
          }).toList());
        } else {
          return Center(child: CircularProgressIndicator());
        }
      },
    );
  }

  @override
  Widget buildSuggestions(BuildContext context) {
    // Son las sugerencias que aparecen cuando la persona escribe
    if (query.isEmpty) {
      return Container();
    }

    return FutureBuilder(
      future: augmentedProvider.searchBooks(query),
      builder: (BuildContext context, AsyncSnapshot<List<Book>> snapshot) {
        print(snapshot.toString());
        if (snapshot.hasData) {
          final books = snapshot.data;

          return ListView(
              children: books.map((book) {
            return ListTile(
              leading: book.extension == "mp4"? Icon(Icons.video_library) : Icon(Icons.image), 
              title: Text(book.name),
              subtitle: Text(book.activity),
              onTap: () async {
                close(context, null);
                  var tempDir = await getApplicationDocumentsDirectory();
                  String fullPath = tempDir.path + "/" + book.name  + "." + book.extension;
                  String filepath = await download2(dio, book.download, fullPath);
                  if (Platform.isAndroid) {
                    _callRA(filepath);
                    //Navigator.pushNamed(context, 'AndroidRA', arguments: <String, String>{ "URL" : book.objectUrl});
                  } else {
                    Navigator.pushNamed(context, 'IOSRA');
                  }
              },
            );
          }).toList());
        } else {
          return Center(child: CircularProgressIndicator());
        }
      },
    );
  }

  Future<Null> _callRA(String assets) async {
    await platform.invokeMethod('showNativeView', {'url': assets});
  }
}
