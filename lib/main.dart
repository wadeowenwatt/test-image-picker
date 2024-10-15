import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:test_image_picker/exts/media_service.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _counter = 0;
  String uriImagePick = "";

  MethodChannel listenerChannel = MethodChannel("HAHAHA");

  @override
  void initState() {
    super.initState();
    setMethodHandler();
  }

  void setMethodHandler() {
    listenerChannel.setMethodCallHandler((call) async {
      if (call.method == 'imagePickerResult') {
        setState(() {
          print(">>> ${call.arguments}");
          uriImagePick = call.arguments;
        });
      }
    });
  }


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Image.file(File(uriImagePick)),
            Text(
              '$_counter',
              style: Theme.of(context).textTheme.headline4,
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () async {
          // await MediaService.getImageAndVideoPermission();

          await MediaService.getImage();
        },
        tooltip: 'Increment',
        child: Icon(Icons.add),
      ),
    );
  }
}
