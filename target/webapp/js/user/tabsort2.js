
/* ��������� */
 /* ������������� ������������ title */
var use_title = false /* added by Clusta */
 /* ����������-�������������� ���������� ���������� */
var use_cookie = true
 /* ������������ ����� ����� (�������) */
var use_inputs = true
 /* ������������ ������� ��� axis (��� String) */
var inputs_force = false;
/* ��������� ����� */

function scells(row) {
 var sarr=[], cells=row.cells;
 for (var i=0; i<cells.length; i++) {
  var c=striptags(cells[i].innerHTML).toLowerCase();
  sarr[i]=c;
 }
 return sarr;
}

function hideres(e) {
 e = e || window.event
 if (27==e.keyCode && window.result) addclass(result, "disnone")
}

addLoadEvent(preptabs);
function preptabs() {
 document.onkeyup=hideres;
 window.verse=false;
 var head, uri=location.pathname, 
  inputs="<br>&gt;= <input class='num' onkeyup='data_filter(this, 1)'> <br>&lt;= <input class='num' onkeyup='data_filter(this, 2)'>",
  input="<br><input class='str' onkeyup='data_filter(this, 3)'>";
 if (use_inputs) {
  window.result=document.createElement("SPAN");
  result.id="num_res";
  result.className="abstopleft";
  document.body.appendChild(result);
 }
 var tex=document.getElementsByTagName("TABLE");
 for (var el=0; el<tex.length; el++) { /*����� ������ � ���������� onclick*/
  var elem=tex[el];
  if (1==elem.nodeType && "TABLE"==elem.tagName && / *sortable\b/i.test(elem.className)) {
   elem.sorted=NaN;
   elem.rarr=[el];
   if (use_cookie) {
   /* �������������� ���������� ���������� */
    
    elem.cookid=new Array(uri+"_tab"+el, "");
    var task=getCookie(elem.cookid[0]);
    if (task) {
     var taskarr, cellid;
     task=task.split(",");
     for (var i=0; i<task.length; i++) {
      if (task[i]) {
       taskarr=task[i].split(":");
       if (!isNaN(parseInt(taskarr[0]))) {
        cellid=Math.abs(parseInt(taskarr[0]));
        sorttab(elem.rows[0].cells[cellid], elem.rows[0], taskarr[1]);
       }
      }
     }
    }
   /* �������������� ���������� ���������� ����� */
   }
   head=elem.rows[0];
   head.onclick=clicktab;
   head.style.cursor="pointer";
/* ���������� ������ ��������� */
   if (use_inputs) {
    var c, isaxis=false;
    for (var k=0; k<head.cells.length; k++) {
     c=head.cells[k];
     if (c.axis) {
      c.innerHTML += ("num"==c.axis) ? inputs : input;
     }
     else if (inputs_force) c.innerHTML += input;
    }
   }
/* ���������� ������ ��������� ����� */
   if (use_title) elem.rows[0].title = "�����������"
   for (var j=1; j<elem.rows.length; j++) {
    if (0==j%2) elem.rows[j].style.backgroundColor="#f0f0f0";
    elem.rows[j].onmouseover=overtab; /*"�����" � ���������*/
    elem.rows[j].onmouseout=overtab;
   }
  }
 }
}

var trcolor, overcolor="#ccf";
function overtab(e) {
 e = e || window.event;
 var obj=e.target || e.srcElement;
 var tr=obj.parentNode;
 if (overcolor==tr.style.backgroundColor) {
  tr.style.backgroundColor=trcolor;
 } 
 else {
  trcolor=tr.style.backgroundColor;
  tr.style.backgroundColor=overcolor;
  /*����� ������� � ������ rgb()*/
  overcolor=tr.style.backgroundColor; 
 }
}

function clicktab(e) {
 e = e || window.event;
 prevent(e);
 var sorttype=false;
 if (!e.ctrlKey && e.shiftKey && !e.altKey) sorttype="num";
 if (e.ctrlKey && !e.shiftKey && e.altKey) sorttype="daten";
 if (e.ctrlKey && !e.shiftKey && !e.altKey) sorttype="datru";
 var obj=e.target || e.srcElement;
 /* ���� � ��������� ������� ��� */
 if ("INPUT"==obj.tagName) return;
 if ("TH"!=obj.tagName && "TD"!=obj.tagName) {
  var testobj=findParent(obj, "TH"); //������ ���������
  if (!testobj) { / * �������� ������������� (� TD � ���������) */
   testobj=findParent(obj, "TD");
   if (this!=testobj.parentNode) testobj=null;
  }
  obj=testobj;
 }
 if (!obj) return;
 if (e.ctrlKey && e.shiftKey && e.altKey) {
  var t=findParent(obj, "TABLE", "sortable");
  if (!t || "object"!=typeof t.cookid) return;
  setCookie(t.cookid[0], "", "", -1) //delete cookie
//  alert(document.cookie)
  location.reload();
  return;
 }
 sorttab(obj, this, sorttype) /* ���������: ������, ������ ���������, ��� ���������� */
}

function sorttab(obj, row0, sorttype) {
 var d1=new Date(); //�������� ��������
 if (!sorttype) {
  sorttype=obj && obj.axis;
  sorttype = (sorttype) ? 
   /str|num|datru|daten/i.exec(sorttype)[0] : "str";
 }
/* this - TR[0] */
 var table=findParent(obj, "TABLE"); //�������
 var i=obj.cellIndex; /*����� ����������� �������*/
/* ������� */
 addclass(obj, "i") /* ��������� ������� CSS: .i {font-style:italic;} */
 var otext=obj.innerHTML, ol=otext.length
 if (8595==otext.charCodeAt(ol-1)) {
  obj.innerHTML=otext.replace(/ .$/i, " &uarr;")
  if (use_title) obj.title="������������� �� ��������"
  if (use_cookie && "object"==typeof table.cookid) table.cookid[1]+=","+(-i)+":"+sorttype
 }
 else if (8593==otext.charCodeAt(ol-1)) {
  obj.innerHTML=otext.replace(/ .$/i, " &darr;")
  if (use_title) obj.title="������������� �� �����������"
  if (use_cookie && "object"==typeof table.cookid) table.cookid[1]+=","+i+":"+sorttype
 }
 else {
  obj.innerHTML+=" &darr;"
  if (use_title) obj.title="������������� �� �����������"
  if (use_cookie && "object"==typeof table.cookid) table.cookid[1]+=","+i+":"+sorttype
 }
 /* �������� ���������� ������� */
 if (!isNaN(table.sorted) && table.sorted!=i) {
  var oldotext=row0.cells[table.sorted].innerHTML
  row0.cells[table.sorted].innerHTML=oldotext.substr(0, oldotext.length-2)
 }
 /* �������� ���������� ������� ����� */
/* ������� ����� */
 if (use_cookie && "object"==typeof table.cookid) {
  var tocut=new RegExp(i+"\\:"+sorttype+"\\,\\-"+i+"\\:"+sorttype+"\\,"+i+"\\:"+sorttype)
  table.cookid[1]=table.cookid[1].replace(tocut, i+":"+sorttype)
//  alert(table.cookid[1])
  setCookie(table.cookid[0], table.cookid[1], "", 0)
 }
 var tbody=table.lastChild; /*TBODY - � �� ����� ������*/
 if (i==table.sorted && sorttype==table.sorttype && "object"==typeof table.rarr) { /*����������*/
  table.rarr=table.rarr.reverse();
 }
 else {
  if ("object"!=typeof table.rarr) table.rarr=[]
  for (var j=1; j<table.rows.length; j++) {
  /*�������� ������� ����� ������� � ����������� ���� ����������*/
   var c=table.rows[j] && table.rows[j].cells[i];
//   table.rows[j].cells[0].innerHTML+="'"+j
   /*������ ����� �� ���� � ������ �������*/
   var cval=(c)?c.innerHTML:"";
   /* "�������������" ������, ������ ������ ��� ������
   rarr[j]={val:table.rows[j], sortcells:scells(table.rows[j])}; */
   var tmp, newval=0, sortcell=striptags(cval).toLowerCase();
   switch (sorttype) {
    case "datru":
     tmp=sortcell.split("."); 
     for (var x=tmp.length-1; x>=0; x--) {
      newval+=parseInt(tmp[x]) * Math.pow(100, x)
     }
     sortcell = (isNaN(newval)) ? Infinity : newval; 
     break
    case "daten":
     tmp=sortcell.split(/\.|\//); 
     if (tmp[2]) newval+=parseInt(tmp[2]) * Math.pow(100, 2);
     if (tmp[0]) newval+=parseInt(tmp[0]) * Math.pow(100, 1);
     if (tmp[1]) newval+=parseInt(tmp[1]);
     sortcell = (isNaN(newval)) ? Infinity : newval; 
     break
    case "num":
     sortcell = Number(sortcell);
     sortcell = (isNaN(sortcell)) ? Infinity : sortcell; 
     break
    default:
     sortcell=String(sortcell)
     break
   }
   table.sorttype=sorttype;
   table.rarr[j-1]={val:table.rows[j], sortcell:sortcell};
   //table.deleteRow(j); /*�� �����!*/
  } /*�������� ������� �����*/
  table.rarr=table.rarr.sort(cmprow);
 } 
 table.sorted=i; /*���������� �����*/
 /*�������� ������ ���������������� ������� � �������*/
 for (var l=0; l<table.rarr.length; l++) {
  if (table.rarr[l] && table.rarr[l].val) {
   if (l%2) table.rarr[l].val.style.backgroundColor="f0f0f0";
   else table.rarr[l].val.style.backgroundColor="fff";
   tbody.appendChild(table.rarr[l].val);
  }
  //������ ����������� � �������, �������� �� ��� �� �������
  //(������ rarr - ������ ������������� ������ �� ������)
 }
 
 /*var d3=new Date(); alert(d3-d1); d1=d3; //����������������� ��� ����� �������*/
}

function cmprow(a, b) {
 a=a.sortcell, b=b.sortcell;
 try { 
  /* verse �����, ���� ��������������� ����. �� ���� 
   - �� �������� ��� ��������� �� ��� */
  if (a > b) return (verse)?-1:1;
  if (a < b) return (verse)?1:-1;
 }
 catch(e){return 0;}
 return 0;
}

/******************** JS Data Filter *******************/

function data_filter(obj, n) {
 var invis_class="disnone_if", q, q_int, qr, qrx, row, 
  val, val_int, c=obj.parentNode, counter=0
  pflag=false, t=findParent(obj, "TABLE"), cid=c.cellIndex,
  fields=c && c.getElementsByTagName("INPUT"),
  field1=fields && fields[0], val1=field1 && field1.value, val1_int=parseInt(val1),
  field2=fields && fields[1], val2=field2 && field2.value, val2_int=parseInt(val2)
 window.result=document.getElementById("num_res")
 result.style.top=getTopLeft(c).top-100
 result.style.left=getTopLeft(c).left+10
 if (!val1 && !val2) {
  delclass(t, "disnone_child")
  return
 }
 if (obj.axis && obj.axis==obj.value && !field2) return
 if (window.pworking) return
 obj.axis=obj.value
 q=obj.value.toLocaleLowerCase()
 q_int=parseInt(q)
 qr=q.substr(1)
 qrx="/^"+qr+"/"
 window.pworking=true
 for (var i=1; i<t.rows.length; i++) {
  row=t.rows[i]
  val=striptags(row.cells[cid].innerHTML).toLocaleLowerCase()
  val_int=parseInt(val)
  switch (n) {
   case 1:
    if ((isNaN(q_int) || val_int>=q_int) && (isNaN(val2_int) || val_int<=val2_int)) {
     delclass(row, invis_class)
     counter++
     pflag = true
    }
    else addclass(row, invis_class)
   break
   case 2:
    if ((isNaN(q_int) || val_int<=q_int) && (isNaN(val1_int) || val_int>=val1_int)) {
     delclass(row, invis_class)
     counter++
     pflag = true
    }
    else addclass(row, invis_class)
   break
   default:
    if (0==q.indexOf(" ")) {
     if (val.indexOf(qr)>-1) {
      delclass(row, invis_class)
      counter++
      pflag = true
     }
     else addclass(row, invis_class)
    }
    else {
     if (0==val.indexOf(q)) {
      delclass(row, invis_class)
      counter++
      pflag = true
     }
     else addclass(row, invis_class)
    }
   break
  }
 }
 result.innerHTML=counter
 if (pflag) {
  delclass(result, "disnone")
  obj.style.color="#000"
  addclass(t, "disnone_child")
 }
 else {
  obj.style.color="#c00"
  delclass(t, "disnone_child")
 }
 window.pworking=false
}

