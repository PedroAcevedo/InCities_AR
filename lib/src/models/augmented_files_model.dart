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
  String objectUrl;
  String thumbnail;

  Book({
    this.id,
    this.name,
    this.description,
    this.objectUrl,
    this.thumbnail,
  });

  Book.fromJsonMap( Map<String, dynamic> json ) {

    id                  = json['id'];
    name                = json['name'];
    description         = json['description'];
    objectUrl           = json['object_url'];
    thumbnail           = json['thumbnail'];
  }

}

