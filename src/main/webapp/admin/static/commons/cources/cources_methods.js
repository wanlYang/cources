export default {
	methods: {
        delCources: function (cources, index) {
            let timers = this.timer;
            if (timers) {
                window.clearTimeout(timers);
                this.timer = null;
            }
            this.timer = window.setTimeout(function () {
                layer.confirm('确定删除此课程表?', {
                    icon: 3,
                    title: '提示信息'
                }, function (index) {
                    var params_del = new URLSearchParams();
                    params_del.append('id', cources.id);
                    axios.post(getRealPath() + "/admin/cources/table/delete/submit", params_del)
                        .then(function (response) {
                            layer.msg("删除成功!");
                            location.reload();
                        })
                        .catch(function (error) { // 请求失败处理
                            layer.msg("删除失败!")
                            console.log(error);
                        });
                });
            }, 300)
        },
        editCources: function (cources) {
            let timers = this.timer;
            if (timers) {
                window.clearTimeout(timers);
                this.timer = null;
            }
            window.sessionStorage.setItem("star_class", cources.star_class);
            const index = layui.layer.open({
                title: "编辑【" + cources.week.describe + "】课程",
                type: 2,
                area: ['500px', '550px'],
                content: getRealPath() + "/admin/cources/table/edit/page",
                success: function (layero, index) {
                    var body = layui.layer.getChildFrame('body', index);
                    var iframeWindow = window[layero.find('iframe')[0]['name']];
                    body.find("#courcesId").val(cources.id);
                    body.find(".name").val(cources.name);
                    body.find(".star_class").val(cources.star_class);
                    body.find(".type").val(cources.type);
                    body.find(".effect").val(cources.effect);
                    body.find(".start_time").val(cources.start_time);
                    body.find(".end_time").val(cources.end_time);
                    if (typeof (iframeWindow.layui.form) != "undefined") {
                        iframeWindow.layui.form.render();
                    }
                },
                end: function () {
                    var cources = JSON.parse(window.sessionStorage.getItem("cources"));

                }
            });
        }
	}
}