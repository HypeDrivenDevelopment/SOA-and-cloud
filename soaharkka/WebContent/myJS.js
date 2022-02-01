window.onload = customize;

function customize(){
	window.document.getElementById('div3').style.display = 'none';
}

function wealth()
{
    window.document.getElementById('div3').style.display = 'none';
	//var q_str = 'type=price&value='+document.getElementById('t3').value;
	var q_str = 'value1='+document.getElementById('t1').value+'&value2='+document.getElementById('t3').value+'&type=wealth';
	doAjax('NewServlet',q_str,'wealth_back','post',0);
}
function wealth_back(result)
{
	if (result.substring(0,5)=='error'){
	   window.document.getElementById('div3').style.display = 'block';
	   window.document.getElementById('div3').innerHTML="<p style='color:red;'><b>"+result.substring(6)+"</b></p>";
   }else{
	   window.document.getElementById('t4').value=""+result;
   }
}

function pension()
{
    window.document.getElementById('div3').style.display = 'none';
	//var q_str = 'type=price&value='+document.getElementById('t3').value;
	var q_str = 'value1='+document.getElementById('t1').value+'&value2='+document.getElementById('t3').value+'&type=pension';
	doAjax('NewServlet',q_str,'pension_back','post',0);
}
function pension_back(result)
{
	if (result.substring(0,5)=='error'){
	   window.document.getElementById('div3').style.display = 'block';
	   window.document.getElementById('div3').innerHTML="<p style='color:red;'><b>"+result.substring(6)+"</b></p>";
   }else{
	   window.document.getElementById('t4').value=""+result;
   }
}
