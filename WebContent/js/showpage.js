$.fn.showPage = function(count) {
	var str = '<a href="javascript:void(0)" class="current">1</a>';
	for (var i = 2; i <= count; i ++) {
		str += '<a href="javascript:void(0)">' + i + '</a>';
	}
	
	var currentObj = $(this);
	currentObj.html("").append($(str));
	
	var objs = currentObj.children("a");
	if (count > 10) {	
		objs.slice(5, count - 5).addClass("show");
		objs.eq(4).after($("<span>...</span>"));
	}
	
	
	objs.unbind("click");
	objs.click(function(){
		if ($(this).hasClass("current")) {
			return this;
		}
		
		objs.removeClass("current"); // 移除超链接上的选中样式
		$(this).addClass("current"); // 给当前点击的这个页码添加选中样式
		
		// var index = parseInt($(this).text());
		var index = $(this).index() + 1; // 获取当前对象的索引值
		
		findByPage(index, this); // 调用外部分页查询的方法
		
		
		if (index < 5 || index > count - 5) {
			return;
		}
		
		if (count > 10) {
			currentObj.children("span").remove(); // 移除这个分页栏中的span标签
			
			var nextObj = $(this).nextAll(); // 获取当前点击的这个页码后面的所有页码对象
			var nextSize = nextObj.length;
			
			if (index >= 5) {
				// 从当前这个页码的后面第3个页码开始到最后5个页码之前的所有页码隐藏
				nextObj.slice(2, nextSize - 5).addClass("show");
				nextObj.slice(0, 2).removeClass("show"); // 当前页面的后面2个页面显示
			
				// 获取当前对象的前面页码
				var prvObj = $(this).prevAll();
				var prvSize = prvObj.length;
			
				prvObj.removeClass("show"); // 当前页面的前面的所有页码都显示
				
				if (prvSize >= 8) { // 如果前面的页面数大于8，则需要隐藏一些
					prvObj.slice(2, index - 6).addClass("show"); // 隐藏第6个开始但当前页码的前2个
		
					// 补上...
					if ($(this).prevAll(".show").length > 0 ) { // 说明当前这个页码前面有隐藏的页码
						$(this).prev().prev().before($("<span>...</span>"));
					}
				}
			
				if ($(this).nextAll(".show").length > 0 ) { // 说明当前这个页码后面有隐藏的页码
					$(this).next().next().after($("<span>...</span>"));
				}
			}
		}
	})
	return this;
}
