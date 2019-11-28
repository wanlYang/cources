layui.use(['form', 'layer','laydate','rate'], function() {
		var form = layui.form
		layer = parent.layer === undefined ? layui.layer : top.layer,
			$ = layui.jquery,
			rate = layui.rate,
			laydate = layui.laydate;
		var date = new Date();
		//渲染
	    var rateOne = rate.render({
	      elem: '#difficulty'  //绑定元素
	      ,choose: function(value){
		     $("#star_class").val(value);
		  },
		  value: window.sessionStorage.getItem("star_class")
	    });
	    window.sessionStorage.removeItem("star_class");
		form.verify({});
		var tempImg = false;
		// 监听表单
		//时间范围

		form.on("submit(editTableCources)", function(data) {
			const index = top.layer.msg('数据提交中,请稍候', {
				icon: 16,
				time: false,
				shade: 0.8
			});
			// 实际使用时的提交信息
			var start_end = data.field.start_end;
			var startAndEnd= new Array();
			startAndEnd = start_end.split("-");
			data.field.start_time = startAndEnd[0];
			data.field.end_time = startAndEnd[1];
			$.ajax({
				type: "POST",
				url: getRealPath() + "/admin/cources/table/edit/submit",
				data: data.field,
				success: function(result) {
					if(result.status == 200) {
						setTimeout(function() {
							layer.close(index);
							const index_p = parent.layer.getFrameIndex(window.name); //获取当前窗口的name
				            parent.layer.close(index_p);
							window.sessionStorage.setItem("cources", JSON.stringify(result.data));
							layer.msg("编辑成功！");
						}, 500);
					} else {
						layer.close(index);
						layer.msg("编辑失败！");
					}
				}
			});
			return false;
		})
	})