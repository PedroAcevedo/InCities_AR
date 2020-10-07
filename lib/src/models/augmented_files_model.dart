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
  int id;
  String name;
  String activity;
  int module;
  String map;
  String download;
  String extension;

  Book(
      {this.id,
      this.name,
      this.activity,
      this.module,
      this.map,
      this.download,
      this.extension});

  Book.fromJsonMap(Map<String, dynamic> json) {
    id = json['ra_material_id'];
    name = json['name'];
    activity = json['activity_title'];
    module = json['module'];
    map = json['map'];
    download = json['url'];
    extension = json['extension'];
  }
}
