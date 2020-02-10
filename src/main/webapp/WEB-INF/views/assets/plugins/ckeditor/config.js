/**
 * @license Copyright (c) 2003-2019, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see https://ckeditor.com/legal/ckeditor-oss-license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here.
	// For complete reference see:
	// https://ckeditor.com/docs/ckeditor4/latest/api/CKEDITOR_config.html

	// The toolbar groups arrangement, optimized for two toolbar rows.
		config.toolbarGroups = [
			{ name: 'styles', groups: [ 'styles' ] },
			{ name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi', 'paragraph' ] },
			{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
			{ name: 'clipboard', groups: [ 'clipboard', 'undo' ] },
			{ name: 'editing', groups: [ 'find', 'selection', 'spellchecker', 'editing' ] },
			{ name: 'links', groups: [ 'links' ] },
			{ name: 'insert', groups: [ 'insert' ] },
			{ name: 'forms', groups: [ 'forms' ] },
			{ name: 'tools', groups: [ 'tools' ] },
			{ name: 'document', groups: [ 'mode', 'document', 'doctools' ] },
			{ name: 'others', groups: [ 'others' ] },
			{ name: 'colors', groups: [ 'colors' ] },
			{ name: 'about', groups: [ 'about' ] }
		];

	// Remove some buttons provided by the standard plugins, which are
	// not needed in the Standard(s) toolbar.
	config.removeButtons = 'Subscript,Superscript,Cut,Copy,Paste,PasteText,PasteFromWord,Anchor,Table,HorizontalRule,Maximize,Source,Indent,Outdent,About';

	// Set the most common block elements.
	config.format_tags = 'p;h1;h2;h3;pre';
	
	config.resize_enabled = false;

	// Simplify the dialog windows.
	config.removeDialogTabs = 'image:advanced;link:advanced';
}


