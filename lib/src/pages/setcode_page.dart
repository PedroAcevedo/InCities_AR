import 'dart:async';

import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:incities_ar/src/providers/augmented_provider.dart';

class SetCodePage extends StatefulWidget {
  const SetCodePage({Key key}) : super(key: key);

  @override
  _SetCodePageState createState() => _SetCodePageState();
}

class _SetCodePageState extends State<SetCodePage> {
  AugmentedProvider augmentedProvider = new AugmentedProvider();

  String _code = '';
  bool _isLoading = false;

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    var loginBtn = new Container(
        margin: EdgeInsets.only(left: 30, right: 30),
        height: 45,
        width: double.infinity,
        child: RaisedButton(
          color: Color(0xff0066CC),
          elevation: 0,
          child: Text(
            "Iniciar RA",
            style: TextStyle(color: Colors.white),
          ),
          shape: RoundedRectangleBorder(
            borderRadius: new BorderRadius.circular(6),
          ),
          onPressed: _submit,
        ));

    return Container(
        child: Scaffold(
      body: Column(
        children: <Widget>[
          Padding(
            padding: EdgeInsets.only(top: 80.0),
          ),
          Image(
            image: AssetImage('assets/LogoInCITIES.png'),
            width: 200.0,
          ),
          SizedBox(
            height: 20.0,
          ),
          Container(
            padding: EdgeInsets.all(50.0),
            child: TextField(
              keyboardType: TextInputType.text,
              decoration: InputDecoration(
                hintText: 'Ingresa el código',
                labelText: 'Código',
                prefixIcon: Icon(Icons.code),
                                 enabledBorder:OutlineInputBorder(
                 borderSide: const BorderSide(color: Colors.grey, width: 1.0),
                 borderRadius: BorderRadius.circular(6),
               ),
                focusedBorder: OutlineInputBorder(
                  borderSide: const BorderSide(color: Colors.grey, width: 1.0),
                  borderRadius: BorderRadius.circular(6),
                ),
              ),
              onChanged: (value) {
                setState(() {
                  _code = value;
                });
              },
            ),
          ),
          SizedBox(
            height: 20.0,
          ),
          _isLoading ? new CircularProgressIndicator() : loginBtn
        ],
      ),
    ));
  }

  void _submit() async {
    FocusScope.of(context).unfocus();
    setState(() => _isLoading = true);
    String g = await augmentedProvider.findCode(_code);
    if (g == "1") {
      setState(() => _isLoading = false);
      Navigator.pushNamed(context, 'menu');
    } else {
      setState(() => _isLoading = false);
      Fluttertoast.showToast(
          msg: "${g.toString()}", toastLength: Toast.LENGTH_SHORT);
    }
  }
}
