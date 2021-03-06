/* Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0 */
var NONE = 'none';
var WbArea = (function() {
	var container, area, role = NONE, self = {}, choose, btns
		, _inited = false, recStart, recStop;

	function _init() {
		container = $(".room.wb.area");
		area = container.find(".wb-area");
		btns = $('.pod-row .pod-container .pod a.choose-btn');
		btns.button()
			.click(function() {
				choose.dialog('open');
				let sel = choose.find('.users').html('');
				let users = $('.user.list .user.entry');
				for (let i = 0; i < users.length; ++i) {
					let u = $(users[i]);
					sel.append($('<option></option>').text(u.attr('title')).val(u.attr('id').substr(4)));
				}
				choose.find('.pod-name').val($(this).data('pod'));
				return false;
			});
		recStart = $('.pod-row .pod-container a.rec-btn.start').button({
			disabled: true
			, icon: "ui-icon-play"
		}).click(function() {
			wbAction('startRecording', '');
			return false;
		});
		recStop = $('.pod-row .pod-container a.rec-btn.stop').button({
			disabled: true
			, icon: "ui-icon-stop"
		}).click(function() {
			wbAction('stopRecording', '');
			return false;
		});
		choose = $('#interview-choose-video');
		choose.dialog({
			modal: true
			, autoOpen: false
			, buttons: [
				{
					text: choose.data('btn-ok')
					, click: function() {
						toggleActivity('broadcastAV', choose.find('.users').val(), choose.find('.pod-name').val());
						$(this).dialog('close');
					}
				}
				, {
					text: choose.data('btn-cancel')
					, click: function() {
						$(this).dialog('close');
					}
				}
			]
		});
		_inited = true;
	}
	function _setRole(_role) {
		if (!_inited) return;
		role = _role;
		if (role !== NONE) {
			btns.show();
		} else {
			btns.hide();
		}
	}
	function _resize(posX, w, h) {
		if (!container || !_inited) return;
		var hh = h - 5;
		container.width(w).height(h).css('left', posX + "px");
		area.width(w).height(hh);
	}
	function _setRecStartEnabled(en) {
		recStart.button("option", "disabled", !en);
	}
	function _setRecStopEnabled(en) {
		recStop.button("option", "disabled", !en);
	}

	self.init = _init;
	self.destroy = function() {};
	self.setRole = _setRole;
	self.resize = _resize;
	self.setRecStartEnabled = _setRecStartEnabled;
	self.setRecStopEnabled = _setRecStopEnabled;
	return self;
})();
