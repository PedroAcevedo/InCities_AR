import 'package:flutter/material.dart';

class SetCodePage extends StatefulWidget {
  const SetCodePage ({Key key}) : super(key: key);

  @override
  _SetCodePageState createState() => _SetCodePageState();
}

class _SetCodePageState extends State<SetCodePage> {

  String _code = '';

  @override
  Widget build(BuildContext context) {
    return Container(
      child: Scaffold(
        appBar: AppBar(title: Text('InCities'),),
        body: Column(
              children: <Widget>[
                Padding(padding: EdgeInsets.only( top: 20.0),),
                FadeInImage(
                  image: NetworkImage('https://images7.alphacoders.com/633/thumb-1920-633262.png'),
                  placeholder: AssetImage('assets/jar-loading.gif'),
                  width: 200.0,
                ),
                SizedBox(height: 20.0,),
                TextField(
                  keyboardType: TextInputType.number,
                  decoration: InputDecoration(
                    border: OutlineInputBorder(borderRadius: BorderRadius.circular(20.0)),
                    hintText: 'Ingresa el código',
                    labelText: 'Código',
                    icon: Icon( Icons.code )
                  ),
                  onChanged: (value) {
                    setState(() {
                      _code = value;
                    });
                  },
                ),
                SizedBox(height: 20.0,),
                RaisedButton(
                  child: Text("Empezar RA"),
                  color: Colors.blue,
                  textColor: Colors.white,
                  shape: StadiumBorder(),
                  onPressed: () => Navigator.pushNamed(context, 'menu'),
                )
              ]
              ,),
      )
    );
  }
}