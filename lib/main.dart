
import 'package:flutter/material.dart';


import 'package:incities_ar/src/pages/menu_page.dart';
import 'package:incities_ar/src/routes/main_routes.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
//import 'package:components/src/pages/home_temp.dart';

void main() => runApp(MyApp());
 
class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Componets App',
      debugShowCheckedModeBanner: false,
      localizationsDelegates: [
        GlobalMaterialLocalizations.delegate,
        GlobalWidgetsLocalizations.delegate,
      ],
       supportedLocales: [
        const Locale('en'), // English
        const Locale('es'), // Español
      ],
      //home: HomePage(),
      initialRoute: '/',
      routes: getAplicationRoutes(),
      onGenerateRoute: ( RouteSettings settings) {

        print("Caller ${ settings.name }");

        return (
          MaterialPageRoute(
            builder: (BuildContext context) => MenuPage()
          )
        );
      },
    );
  }
}