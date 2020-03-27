class Books {

  List<Book> items = new List();

  Books();

  Books.fromJsonList( List<dynamic> jsonList  ) {

    if ( jsonList == null ) return;

    for ( var item in jsonList  ) {
      final book = new Book.fromJsonMap(item);
      items.add( book );
    }

  }

}

class Book {
  String id;
  String name;
  String description;
  Map<String, dynamic> augmentedReferences;

  Book({
    this.id,
    this.name,
    this.description,
    this.augmentedReferences,
  });

  Book.fromJsonMap( Map<String, dynamic> json ) {

    id                  = json['id'];
    name                = json['name'];
    description         = json['description'];
    augmentedReferences = json['augmented_references'];
  }

}

