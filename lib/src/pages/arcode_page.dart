import 'package:arcore_plugin/arcore_plugin.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:vector_math/vector_math_64.dart' as vector;

class ARCodePage extends StatefulWidget {
  @override
  _ARCodePageState createState() => _ARCodePageState();
}

class _ARCodePageState extends State<ARCodePage> {
  ArCoreViewController arCoreController;

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        body: Container()
      ),
    );
  }

}