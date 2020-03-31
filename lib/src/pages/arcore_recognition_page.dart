import 'dart:io';
import 'package:flutter/services.dart' show rootBundle;
import 'package:arcore_plugin/arcore_plugin.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:fluttertoast/fluttertoast.dart';

class TextViewExample extends StatefulWidget {
  @override
  _TextViewExampleState createState() => _TextViewExampleState();
}

class _TextViewExampleState extends State<TextViewExample> {
  String recongizedImage;
  ArCoreViewController arCoreViewController;

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    Size screenSize = MediaQuery.of(context).size;
    return Scaffold(
        backgroundColor: Colors.blue,
        appBar: AppBar(
          title: const Text('ArCoreViewExample'),
          backgroundColor: Colors.black,
          centerTitle: true,
        ),
        body: Center(
            child: ArCoreView(
          focusBox: Container(
            width: screenSize.width * 0.5,
            height: screenSize.width * 0.5,
            decoration: BoxDecoration(
                border: Border.all(width: 1, style: BorderStyle.solid)),
          ),
          width: screenSize.width,
          height: screenSize.height,
          onImageRecognized: _onImageRecognized,
          onArCoreViewCreated: _onTextViewCreated,
        )));
  }

  void _onTextViewCreated(ArCoreViewController controller) {
    arCoreViewController = controller;
    controller.getArCoreView();
  }

  void _onImageRecognized(String recImgName) {
    print("image recongized: $recImgName");
    _showToast("image recongized: $recImgName");

    // you can pause the image recognition via arCoreViewController.pauseImageRecognition();
    // resume it via arCoreViewController.resumeImageRecognition();
  }

  void _showToast(String message) {
    Fluttertoast.showToast(
        msg: message,
        toastLength: Toast.LENGTH_SHORT,
        gravity: ToastGravity.BOTTOM,
        backgroundColor: Colors.red,
        textColor: Colors.white,
        fontSize: 20.0);
  }
}