import 'dart:io';

import 'package:flutter/material.dart';
import 'package:incities_ar/src/models/augmented_files_model.dart';
import 'package:incities_ar/src/providers/augmented_provider.dart';

class SearchDelegatePage extends SearchDelegate {
  final augmentedProvider = AugmentedProvider();

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
              leading: Image(
              image: NetworkImage(
                 book.thumbnail.isEmpty?"https://www.tibs.org.tw/images/default.jpg":book.thumbnail),
              ),
              title: Text(book.name),
              subtitle: Text(book.description),
              onTap: () async {
                close(context, null);
                if (Platform.isAndroid) {
                  Navigator.pushNamed(context, 'AndroidRA', arguments: <String, String>{ "URL" : book.objectUrl});
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
      return Container(
      );
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
              leading: Image(
              image: NetworkImage(
                 book.thumbnail.isEmpty?"https://www.tibs.org.tw/images/default.jpg":book.thumbnail),
              ),
              title: Text(book.name),
              subtitle: Text(book.description),
              onTap: () async {
                close(context, null);
                if (Platform.isAndroid) {
                  Navigator.pushNamed(context, 'AndroidRA', arguments: <String, String>{ "URL" : book.objectUrl});
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
}
