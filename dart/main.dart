
import 'JewishDate.dart';
enum JewishHolidays
{
  Purim,
  Pesach
}
List holidays = 
[ for (var j in JewishHolidays.values) j.toString().split('.').last];
int main() {
  JewishDate j= new JewishDate.now();
  print('Hello, World! Today is ${j.year()} ${j.monthID().toString().split(".").last} ${j.dayInMonth()}\n');
  int x = 7;
  int y = -1;
  print ("${y%x}");
  return 0;
}
