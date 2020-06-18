class Books {
  List<Book> items = new List();

  Books();

  Books.fromJsonList(List<dynamic> jsonList) {
    if (jsonList == null) return;

    for (var item in jsonList) {
      final book = new Book.fromJsonMap(item);
      items.add(book);
    }
  }
}

class Book {
  String id;
  String name;
  String activity;
  String module;
  String tag;
  String download;
  String extension;

  Book(
      {this.id,
      this.name,
      this.activity,
      this.module,
      this.tag,
      this.download,
      this.extension});

  Book.fromJsonMap(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'];
    activity = json['activity'];
    module = json['module'];
    tag = json['tag'];
    download = json['download'];
    extension = json['extension'];
  }
}
