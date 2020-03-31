import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:incities_ar/src/providers/augmented_provider.dart';

class SetCodePage extends StatefulWidget {
  const SetCodePage ({Key key}) : super(key: key);

  @override
  _SetCodePageState createState() => _SetCodePageState();
}

class _SetCodePageState extends State<SetCodePage> {

AugmentedProvider augmentedProvider = new AugmentedProvider();
String _code = '';

@override
 Widget build(BuildContext context) {
   return Container(
       child: Scaffold(
       body: Column(
             children: <Widget>[
               Padding(padding: EdgeInsets.only( top: 80.0),),
               Image(
                 image: AssetImage('assets/LogoInCITIES.png'),
                 width: 200.0,
               ),
             SizedBox(height: 20.0,),
             Container(
             padding: EdgeInsets.all(50.0),
             child: TextField(
                 keyboardType: TextInputType.number,
                 decoration: InputDecoration(
                   border: OutlineInputBorder(borderRadius: BorderRadius.circular(20.0)),
                   hintText: 'Ingresa el código',
                   labelText: 'Código',
                   prefixIcon: Icon( Icons.code ),
                 ),
                 onChanged: (value) {
                   setState(() {
                     _code = value;
                   });
                 },
               ),
               ),
               SizedBox(height: 20.0,),
               RaisedButton(
                 child: Text("Empezar RA"),
                 color: Colors.blue,
                 textColor: Colors.white,
                 shape: StadiumBorder(),
                 onPressed: () async {
                   FocusScope.of(context).unfocus();
                   String g = await augmentedProvider.findCode(_code);
                   if(g == "1"){
                     Navigator.pushNamed(context, 'menu');
                   }else{
                     Fluttertoast.showToast(
                        msg: "${g.toString()}",
                        toastLength: Toast.LENGTH_SHORT
                    );
                   }
                },
               )
             ]
             ,),
     )
   );
 }

}