layui.use(['form', 'layer','jquery'], function() {
	var form = layui.form
	layer = parent.layer === undefined ? layui.layer : top.layer,
		$ = layui.jquery,
	// 监听表单
	form.on("submit(editAdmin)", function(data) {
		var index = top.layer.msg('数据提交中,请稍候', {
			icon: 16,
			time: false,
			shade: 0.8
		});
		//实际使用时的提交信息
		$.ajax({
			type: "POST",
			url: getRealPath() + "/admin/cources/table/admin/edit/submit",
			data: data.field,
			success: function(result) {
				if(result.status == 200) {
					setTimeout(function() {
						top.layer.close(index);
						top.layer.msg("" + result.message);
						layer.closeAll("iframe");
						parent.location.reload();
					}, 500);
				} else {
					top.layer.close(index);
					top.layer.msg("编辑失败！" + result.message);
				}
			}
		});
		return false;
	})
})