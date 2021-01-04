let header = new Vue({
	el:"#header",//这个vue对象的作用范围
	data:{
		types:[],//所有的类型
		onlogin: false,//是否登陆
		mid:"",
		memberInfo:{} , //当前登陆对象
		
	},
	mounted:function(){//vue渲染完成后要执行的方法
		//发送一个请求获取所有店铺类型信息 跟 $.get()相似， axios.get(请求地址，请求参数).then(回调函数)
		axios.get("type",{params:{op:"finds"}}).then( rt => {
			
			if(rt.status ==200){//说明成功
				this.types = rt.data;  //注意this不能少  说明是当前这个vue对象中的data的types属性	
			}
		})
		//发送一个请求去获取用户登陆信息
		axios.get("member",{params:{op:"check1"}}).then( rt => {
		
			if(rt.status ==200 && rt.data.code ==200){//说明成功
				this.onlogin = true;  
				//console.log(this.memberInfo);
				this.memberInfo = rt.data.data;
				this.mid=rt.data.data.mid;
				//console.log(this.mid);
			}else{
				this.onlogin = false;  
				this.memberInfo = {};
			}
		})
	},
	methods:{
		findByTid: function(tid){
			console.info(tid);
		}
	}
	
})

function showMsg(msg,color,callback){
	$("#popup_info").text(msg).css("color",color);
	$(".popup_con").fadeIn('fast',function(){
		setTimeout(function() {
			$(".popup_con").fadeOut('fast',callback);
		}, 2000);
		
	})
}
function excel(){
	$.post("member", {op:"excel"},data =>{
				location.href='login.html';
		},"json");

		
	}