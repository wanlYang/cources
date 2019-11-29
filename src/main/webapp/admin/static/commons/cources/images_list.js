layui.use(['form', 'layer', 'table', 'laytpl','laydate','jquery'], function() {
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : top.layer,
		$ = layui.jquery,
		laytpl = layui.laytpl,
		table = layui.table,
		laydate = layui.laydate;
	var tableIns = table.render({
		elem: '#courcesList',
		url: getRealPath() + '/admin/cources/table/images/list/submit',
		cellMinWidth: 95,
		method: "POST",
		height: "full-125",
        where: {'admin_id' : currentAdminId_Index},
		id: "courcesListTable",
		cols: [
				[	
				{
					sort: true,
					field: "id",
					title: "ID",
					align: "center",
				},
                {
                    field: 'storename',
                    title: '门店名称',
                    style: 'cursor: pointer;',
                    align: 'center',
					templet:function (d) {
                    	var store = "";
						$.ajax({
							type: "POST",
							async: false,
							url: getRealPath() + "/admin/cources/table/images/get/front",
							data: {"storeid":d.storeFront.id},
							success: function(result) {
								store = result.data.name
							}
						});
						return store;
					}
                },
				{
					field: 'src',
					title: '课程表图',
					event: 'preview',
                    style: 'cursor: pointer;',
					align: 'center',
					templet: function(d) {
						if(d.src == null || d.src == ""){
							return "未设置";
						}
						return "<img title='点击预览' src='"+ getRealPath() + '/'+d.src+"' class='cover'/>";
					}
				},
				{
					title: '操作',
					templet: '#courcesListBar',
					fixed: "right",
					align: "center"
				}
			]
		],
		text: {
			none: '暂无相关数据' // 默认：无数据。注：该属性为 layui 2.2.5 开始新增
		},
		response: {
			statusName: 'status', // 规定数据状态的字段名称，默认：code
			statusCode: 200, // 规定成功的状态码，默认：0
			msgName: 'message', // 规定状态信息的字段名称，默认：msg
			countName: 'count', // 规定数据总数的字段名称，默认：count
			dataName: 'data' // 规定数据列表的字段名称，默认：data
		},
		toolbar: true
	});
	// 添加
	function addCources() {
		layer.msg("此功能暂时关闭!");
		return;
		var index = layui.layer.open({
			title: "添加课程表",
			type: 2,
			content: getRealPath() + "/admin/cources/table/images/add",
			success: function(layero, index) {
				setTimeout(function() {
					layui.layer.tips('点击此处返回列表', '.layui-layer-setwin .layui-layer-close', {
						tips: 3
					});
				}, 500)
			},
			end: function() {
				$(window).unbind("resize");
			}
		})
		layui.layer.full(index);
		window.sessionStorage.setItem("index", index);
		// 改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
		$(window).on("resize", function() {
			layui.layer.full(window.sessionStorage.getItem("index"));
		})
	}
	$(".addNews_btn").click(function() {
		addCources();
	})
	// 列表操作
	table.on('tool(courcesList)', function(obj) {
		var layEvent = obj.event,
			data = obj.data;
		// 监听操作
		if(layEvent === 'edit') { // 删除
			var index = layui.layer.open({
				title: "编辑课程表",
				type: 2,
				content: getRealPath() + "/admin/cources/table/images/edit",
				success: function(layero, index) {
					const body = layui.layer.getChildFrame('body', index);
					const iframeWindow = window[layero.find('iframe')[0]['name']];
					$.ajax({
						type: "POST",
						async: false,
						url: getRealPath() + "/admin/cources/table/images/get/front",
						data: {"storeid":data.storeFront.id},
						success: function(result) {
							body.find(".storename").val(result.data.name);
						}
					});
					body.find(".id").val(data.storeFront.id);
					if (data.src!= ''&&data.src!=null){
						body.find("#tableImg")[0].src = getRealPath()+data.src;
					}
					setTimeout(function() {
						layui.layer.tips('点击此处返回列表', '.layui-layer-setwin .layui-layer-close', {
							tips: 3
						});
					}, 500)
				},
				end: function() {
					$(window).unbind("resize");
				}
			})
			layui.layer.full(index);
			window.sessionStorage.setItem("index", index);
			// 改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
			$(window).on("resize", function() {
				layui.layer.full(window.sessionStorage.getItem("index"));
			})
		} else if(layEvent === 'preview') {//显示大图
			if (data.src != '' && data.src != null){
				preview_img(getRealPath() + "/"+data.src);
			}

        } else if(layEvent === 'del') {
			layer.confirm('确定删除此课表图片?', {
				icon: 3,
				title: '提示信息'
			}, function (index) {
				$.ajax({
					type: "POST",
					async: false,
					url: getRealPath() + "/admin/cources/table/images/delete/submit",
					data: {"id":data.id},
					success: function(result) {
						if (result.status == 200){
							location.reload()
							layer.msg("删除成功!")
							obj.update({
								src: ''
							});
						}
					}
				});
			});
        }
	});
})