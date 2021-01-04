//判断是否登陆
let back_index = new Vue({
	el:"#back_index",//这个vue对象的作用范围
	data:{
		
		onlogin: false,//是否登陆
		Info:{} , //当前登陆对象
		info:"",//身份   0商家  1管理员
	},
	mounted:function(){//vue渲染完成后要执行的方法
		axios.get("../../member",{params:{op:"check"}}).then( rt => {
			
			if(rt.status ==200 && rt.data.code ==200){//说明成功
				console.log("卖家");
				this.onlogin = true;  
				this.Info = rt.data.data;
				this.info="member";
				return;
			}else{
				//发送一个请求去获取用户登陆信息
				axios.get("../../admin",{params:{op:"check"}}).then( rt => {
			
					if(rt.status ==200 && rt.data.code ==200){//说明成功
						console.log("管理员");
						this.onlogin = true;  
						this.Info = rt.data.data;
						this.info="admin";
						return;
						
					}else{
						showMsg("请先登录...","red",function(){location.href='../../login_s.html'});
					}
				})
			}
		})
//			let info = location.search; // ?***  location.hash //#***
//			info=info.replace("?","");	
//			if(info == "0"){
//				axios.get("../../member",{params:{op:"check"}}).then( rt => {
//						
//					if(rt.status ==200 && rt.data.code ==200){//说明成功
//						console.log("卖家");
//						this.onlogin = true;  
//						this.Info = rt.data.data;
//						this.info="member";
//						return;
//					}else{
//						this.onlogin = false;  
//						this.Info = {};
//					}
//				})
//			}else if(info == "1"){
//				//发送一个请求去获取用户登陆信息
//					axios.get("../../admin",{params:{op:"check"}}).then( rt => {
//				
//					if(rt.status ==200 && rt.data.code ==200){//说明成功
//						console.log("管理员");
//						this.onlogin = true;  
//						this.Info = rt.data.data;
//						this.info="admin";
//						return;
//					
//						this.onlogin = false;  
//						this.Info = {};
//					}
//				})
//			}else{
//				showMsg("请先登录...","red",function(){location.href='../../login_s.html'});
//			}
//	
		
		
		
		
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