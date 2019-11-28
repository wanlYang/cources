layui.use(['form', 'layer','jquery'], function() {
	var form = layui.form
	layer = parent.layer === undefined ? layui.layer : top.layer,
		$ = layui.jquery,


	//获取店面信息
	$.ajax({
		type: "POST",
		async: false,
		url: getRealPath() + "/admin/cources/table/week/list/front",
		data: {"admin_id":currentAdminId_Index},
		success: function(result) {
			if(result.status == 200) {
				var html = "";
				$.each(result.data,function (index,item) {
					html += '<option value="'+item.id+'">'+item.name+'</option>';

				})
				$("#store").append(html);
				form.render();
				layer.msg("获取数据成功!")
			} else {
				top.layer.close(index);
				top.layer.msg("添加失败！" + result.message);
			}
		}
	});

	// 监听表单
	form.on("submit(addAdmin)", function(data) {
		var index = top.layer.msg('数据提交中,请稍候', {
			icon: 16,
			time: false,
			shade: 0.8
		});

		// 实际使用时的提交信息
		$.ajax({
			type: "POST",
			url: getRealPath() + "/admin/cources/table/admin/add/submit",
			data: data.field,
			success: function(result) {
				if(result.status == 200) {
					setTimeout(function() {
						top.layer.close(index);
						top.layer.msg("添加成功！" + result.message);
						layer.closeAll("iframe");
						parent.location.reload();
					}, 500);
				} else {
					top.layer.close(index);
					top.layer.msg("添加失败！" + result.message);
				}
			}
		});
		return false;
	})
})