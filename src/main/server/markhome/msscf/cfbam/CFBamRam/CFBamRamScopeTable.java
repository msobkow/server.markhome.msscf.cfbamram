
// Description: Java 11 in-memory RAM DbIO implementation for Scope.

/*
 *	server.markhome.msscf.CFBam
 *
 *	Copyright (c) 2020-2025 Mark Stephen Sobkow
 *	
 *
 *	Manufactured by MSS Code Factory 2.13
 */

package server.markhome.msscf.cfbam.CFBamRam;

import java.math.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import org.apache.commons.codec.binary.Base64;
import org.msscf.msscf.cflib.CFLib.*;
import server.markhome.msscf.cfsec.CFSec.*;
import server.markhome.msscf.cfint.CFInt.*;
import server.markhome.msscf.cfbam.CFBam.*;
import server.markhome.msscf.cfbam.CFBamObj.*;
import server.markhome.msscf.cfsec.CFSecObj.*;
import server.markhome.msscf.cfint.CFIntObj.*;
import server.markhome.msscf.cfbam.CFBamObj.*;

/*
 *	CFBamRamScopeTable in-memory RAM DbIO implementation
 *	for Scope.
 */
public class CFBamRamScopeTable
	implements ICFBamScopeTable
{
	private ICFBamSchema schema;
	private Map< CFBamScopePKey,
				CFBamScopeBuff > dictByPKey
		= new HashMap< CFBamScopePKey,
				CFBamScopeBuff >();
	private Map< CFBamScopeByTenantIdxKey,
				Map< CFBamScopePKey,
					CFBamScopeBuff >> dictByTenantIdx
		= new HashMap< CFBamScopeByTenantIdxKey,
				Map< CFBamScopePKey,
					CFBamScopeBuff >>();

	public CFBamRamScopeTable( ICFBamSchema argSchema ) {
		schema = argSchema;
	}

	public void createScope( CFSecAuthorization Authorization,
		CFBamScopeBuff Buff )
	{
		final String S_ProcName = "createScope";
		CFBamScopePKey pkey = schema.getFactoryScope().newPKey();
		pkey.setClassCode( Buff.getClassCode() );
		pkey.setRequiredTenantId( Buff.getRequiredTenantId() );
		pkey.setRequiredId( ((CFBamRamTenantTable)schema.getTableTenant()).nextScopeIdGen( Authorization,
			Buff.getRequiredTenantId() ) );
		Buff.setRequiredTenantId( pkey.getRequiredTenantId() );
		Buff.setRequiredId( pkey.getRequiredId() );
		CFBamScopeByTenantIdxKey keyTenantIdx = schema.getFactoryScope().newTenantIdxKey();
		keyTenantIdx.setRequiredTenantId( Buff.getRequiredTenantId() );

		// Validate unique indexes

		if( dictByPKey.containsKey( pkey ) ) {
			throw new CFLibPrimaryKeyNotNewException( getClass(), S_ProcName, pkey );
		}

		// Validate foreign keys

		{
			boolean allNull = true;
			allNull = false;
			if( ! allNull ) {
				if( null == schema.getTableTenant().readDerivedByIdIdx( Authorization,
						Buff.getRequiredTenantId() ) )
				{
					throw new CFLibUnresolvedRelationException( getClass(),
						S_ProcName,
						"Owner",
						"Tenant",
						"Tenant",
						null );
				}
			}
		}

		// Proceed with adding the new record

		dictByPKey.put( pkey, Buff );

		Map< CFBamScopePKey, CFBamScopeBuff > subdictTenantIdx;
		if( dictByTenantIdx.containsKey( keyTenantIdx ) ) {
			subdictTenantIdx = dictByTenantIdx.get( keyTenantIdx );
		}
		else {
			subdictTenantIdx = new HashMap< CFBamScopePKey, CFBamScopeBuff >();
			dictByTenantIdx.put( keyTenantIdx, subdictTenantIdx );
		}
		subdictTenantIdx.put( pkey, Buff );

	}

	public CFBamScopeBuff readDerived( CFSecAuthorization Authorization,
		CFBamScopePKey PKey )
	{
		final String S_ProcName = "CFBamRamScope.readDerived";
		CFBamScopePKey key = schema.getFactoryScope().newPKey();
		key.setRequiredTenantId( PKey.getRequiredTenantId() );
		key.setRequiredId( PKey.getRequiredId() );
		CFBamScopeBuff buff;
		if( dictByPKey.containsKey( key ) ) {
			buff = dictByPKey.get( key );
		}
		else {
			buff = null;
		}
		return( buff );
	}

	public CFBamScopeBuff lockDerived( CFSecAuthorization Authorization,
		CFBamScopePKey PKey )
	{
		final String S_ProcName = "CFBamRamScope.readDerived";
		CFBamScopePKey key = schema.getFactoryScope().newPKey();
		key.setRequiredTenantId( PKey.getRequiredTenantId() );
		key.setRequiredId( PKey.getRequiredId() );
		CFBamScopeBuff buff;
		if( dictByPKey.containsKey( key ) ) {
			buff = dictByPKey.get( key );
		}
		else {
			buff = null;
		}
		return( buff );
	}

	public CFBamScopeBuff[] readAllDerived( CFSecAuthorization Authorization ) {
		final String S_ProcName = "CFBamRamScope.readAllDerived";
		CFBamScopeBuff[] retList = new CFBamScopeBuff[ dictByPKey.values().size() ];
		Iterator< CFBamScopeBuff > iter = dictByPKey.values().iterator();
		int idx = 0;
		while( iter.hasNext() ) {
			retList[ idx++ ] = iter.next();
		}
		return( retList );
	}

	public CFBamScopeBuff[] readDerivedByTenantIdx( CFSecAuthorization Authorization,
		long TenantId )
	{
		final String S_ProcName = "CFBamRamScope.readDerivedByTenantIdx";
		CFBamScopeByTenantIdxKey key = schema.getFactoryScope().newTenantIdxKey();
		key.setRequiredTenantId( TenantId );

		CFBamScopeBuff[] recArray;
		if( dictByTenantIdx.containsKey( key ) ) {
			Map< CFBamScopePKey, CFBamScopeBuff > subdictTenantIdx
				= dictByTenantIdx.get( key );
			recArray = new CFBamScopeBuff[ subdictTenantIdx.size() ];
			Iterator< CFBamScopeBuff > iter = subdictTenantIdx.values().iterator();
			int idx = 0;
			while( iter.hasNext() ) {
				recArray[ idx++ ] = iter.next();
			}
		}
		else {
			Map< CFBamScopePKey, CFBamScopeBuff > subdictTenantIdx
				= new HashMap< CFBamScopePKey, CFBamScopeBuff >();
			dictByTenantIdx.put( key, subdictTenantIdx );
			recArray = new CFBamScopeBuff[0];
		}
		return( recArray );
	}

	public CFBamScopeBuff readDerivedByIdIdx( CFSecAuthorization Authorization,
		long TenantId,
		long Id )
	{
		final String S_ProcName = "CFBamRamScope.readDerivedByIdIdx() ";
		CFBamScopePKey key = schema.getFactoryScope().newPKey();
		key.setRequiredTenantId( TenantId );
		key.setRequiredId( Id );

		CFBamScopeBuff buff;
		if( dictByPKey.containsKey( key ) ) {
			buff = dictByPKey.get( key );
		}
		else {
			buff = null;
		}
		return( buff );
	}

	public CFBamScopeBuff readBuff( CFSecAuthorization Authorization,
		CFBamScopePKey PKey )
	{
		final String S_ProcName = "CFBamRamScope.readBuff";
		CFBamScopeBuff buff = readDerived( Authorization, PKey );
		if( ( buff != null ) && ( ! buff.getClassCode().equals( "a801" ) ) ) {
			buff = null;
		}
		return( buff );
	}

	public CFBamScopeBuff lockBuff( CFSecAuthorization Authorization,
		CFBamScopePKey PKey )
	{
		final String S_ProcName = "lockBuff";
		CFBamScopeBuff buff = readDerived( Authorization, PKey );
		if( ( buff != null ) && ( ! buff.getClassCode().equals( "a801" ) ) ) {
			buff = null;
		}
		return( buff );
	}

	public CFBamScopeBuff[] readAllBuff( CFSecAuthorization Authorization )
	{
		final String S_ProcName = "CFBamRamScope.readAllBuff";
		CFBamScopeBuff buff;
		ArrayList<CFBamScopeBuff> filteredList = new ArrayList<CFBamScopeBuff>();
		CFBamScopeBuff[] buffList = readAllDerived( Authorization );
		for( int idx = 0; idx < buffList.length; idx ++ ) {
			buff = buffList[idx];
			if( ( buff != null ) && buff.getClassCode().equals( "a801" ) ) {
				filteredList.add( buff );
			}
		}
		return( filteredList.toArray( new CFBamScopeBuff[0] ) );
	}

	public CFBamScopeBuff readBuffByIdIdx( CFSecAuthorization Authorization,
		long TenantId,
		long Id )
	{
		final String S_ProcName = "CFBamRamScope.readBuffByIdIdx() ";
		CFBamScopeBuff buff = readDerivedByIdIdx( Authorization,
			TenantId,
			Id );
		if( ( buff != null ) && buff.getClassCode().equals( "a801" ) ) {
			return( (CFBamScopeBuff)buff );
		}
		else {
			return( null );
		}
	}

	public CFBamScopeBuff[] readBuffByTenantIdx( CFSecAuthorization Authorization,
		long TenantId )
	{
		final String S_ProcName = "CFBamRamScope.readBuffByTenantIdx() ";
		CFBamScopeBuff buff;
		ArrayList<CFBamScopeBuff> filteredList = new ArrayList<CFBamScopeBuff>();
		CFBamScopeBuff[] buffList = readDerivedByTenantIdx( Authorization,
			TenantId );
		for( int idx = 0; idx < buffList.length; idx ++ ) {
			buff = buffList[idx];
			if( ( buff != null ) && buff.getClassCode().equals( "a801" ) ) {
				filteredList.add( (CFBamScopeBuff)buff );
			}
		}
		return( filteredList.toArray( new CFBamScopeBuff[0] ) );
	}

	public void updateScope( CFSecAuthorization Authorization,
		CFBamScopeBuff Buff )
	{
		CFBamScopePKey pkey = schema.getFactoryScope().newPKey();
		pkey.setClassCode( Buff.getClassCode() );
		pkey.setRequiredTenantId( Buff.getRequiredTenantId() );
		pkey.setRequiredId( Buff.getRequiredId() );
		CFBamScopeBuff existing = dictByPKey.get( pkey );
		if( existing == null ) {
			throw new CFLibStaleCacheDetectedException( getClass(),
				"updateScope",
				"Existing record not found",
				"Scope",
				pkey );
		}
		if( existing.getRequiredRevision() != Buff.getRequiredRevision() ) {
			throw new CFLibCollisionDetectedException( getClass(),
				"updateScope",
				pkey );
		}
		Buff.setRequiredRevision( Buff.getRequiredRevision() + 1 );
		CFBamScopeByTenantIdxKey existingKeyTenantIdx = schema.getFactoryScope().newTenantIdxKey();
		existingKeyTenantIdx.setRequiredTenantId( existing.getRequiredTenantId() );

		CFBamScopeByTenantIdxKey newKeyTenantIdx = schema.getFactoryScope().newTenantIdxKey();
		newKeyTenantIdx.setRequiredTenantId( Buff.getRequiredTenantId() );

		// Check unique indexes

		// Validate foreign keys

		{
			boolean allNull = true;

			if( allNull ) {
				if( null == schema.getTableTenant().readDerivedByIdIdx( Authorization,
						Buff.getRequiredTenantId() ) )
				{
					throw new CFLibUnresolvedRelationException( getClass(),
						"updateScope",
						"Owner",
						"Tenant",
						"Tenant",
						null );
				}
			}
		}

		// Update is valid

		Map< CFBamScopePKey, CFBamScopeBuff > subdict;

		dictByPKey.remove( pkey );
		dictByPKey.put( pkey, Buff );

		subdict = dictByTenantIdx.get( existingKeyTenantIdx );
		if( subdict != null ) {
			subdict.remove( pkey );
		}
		if( dictByTenantIdx.containsKey( newKeyTenantIdx ) ) {
			subdict = dictByTenantIdx.get( newKeyTenantIdx );
		}
		else {
			subdict = new HashMap< CFBamScopePKey, CFBamScopeBuff >();
			dictByTenantIdx.put( newKeyTenantIdx, subdict );
		}
		subdict.put( pkey, Buff );

	}

	public void deleteScope( CFSecAuthorization Authorization,
		CFBamScopeBuff Buff )
	{
		final String S_ProcName = "CFBamRamScopeTable.deleteScope() ";
		String classCode;
		CFBamScopePKey pkey = schema.getFactoryScope().newPKey();
		pkey.setClassCode( Buff.getClassCode() );
		pkey.setRequiredTenantId( Buff.getRequiredTenantId() );
		pkey.setRequiredId( Buff.getRequiredId() );
		CFBamScopeBuff existing = dictByPKey.get( pkey );
		if( existing == null ) {
			return;
		}
		if( existing.getRequiredRevision() != Buff.getRequiredRevision() )
		{
			throw new CFLibCollisionDetectedException( getClass(),
				"deleteScope",
				pkey );
		}
		CFBamScopeByTenantIdxKey keyTenantIdx = schema.getFactoryScope().newTenantIdxKey();
		keyTenantIdx.setRequiredTenantId( existing.getRequiredTenantId() );

		// Validate reverse foreign keys

		if( schema.getTableSchemaDef().readDerivedByIdIdx( Authorization,
					existing.getRequiredTenantId(),
					existing.getRequiredId() ) != null )
		{
			throw new CFLibDependentsDetectedException( getClass(),
				"deleteScope",
				"Superclass",
				"SuperClass",
				"SchemaDef",
				pkey );
		}

		if( schema.getTableSchemaRef().readDerivedByIdIdx( Authorization,
					existing.getRequiredTenantId(),
					existing.getRequiredId() ) != null )
		{
			throw new CFLibDependentsDetectedException( getClass(),
				"deleteScope",
				"Superclass",
				"SuperClass",
				"SchemaRef",
				pkey );
		}

		if( schema.getTableServerMethod().readDerivedByIdIdx( Authorization,
					existing.getRequiredTenantId(),
					existing.getRequiredId() ) != null )
		{
			throw new CFLibDependentsDetectedException( getClass(),
				"deleteScope",
				"Superclass",
				"SuperClass",
				"ServerMethod",
				pkey );
		}

		if( schema.getTableTable().readDerivedByIdIdx( Authorization,
					existing.getRequiredTenantId(),
					existing.getRequiredId() ) != null )
		{
			throw new CFLibDependentsDetectedException( getClass(),
				"deleteScope",
				"Superclass",
				"SuperClass",
				"Table",
				pkey );
		}

		if( schema.getTableClearDep().readDerivedByIdIdx( Authorization,
					existing.getRequiredTenantId(),
					existing.getRequiredId() ) != null )
		{
			throw new CFLibDependentsDetectedException( getClass(),
				"deleteScope",
				"Superclass",
				"SuperClass",
				"ClearDep",
				pkey );
		}

		if( schema.getTableDelDep().readDerivedByIdIdx( Authorization,
					existing.getRequiredTenantId(),
					existing.getRequiredId() ) != null )
		{
			throw new CFLibDependentsDetectedException( getClass(),
				"deleteScope",
				"Superclass",
				"SuperClass",
				"DelDep",
				pkey );
		}

		if( schema.getTableIndex().readDerivedByIdIdx( Authorization,
					existing.getRequiredTenantId(),
					existing.getRequiredId() ) != null )
		{
			throw new CFLibDependentsDetectedException( getClass(),
				"deleteScope",
				"Superclass",
				"SuperClass",
				"Index",
				pkey );
		}

		if( schema.getTablePopDep().readDerivedByIdIdx( Authorization,
					existing.getRequiredTenantId(),
					existing.getRequiredId() ) != null )
		{
			throw new CFLibDependentsDetectedException( getClass(),
				"deleteScope",
				"Superclass",
				"SuperClass",
				"PopDep",
				pkey );
		}

		if( schema.getTableRelation().readDerivedByIdIdx( Authorization,
					existing.getRequiredTenantId(),
					existing.getRequiredId() ) != null )
		{
			throw new CFLibDependentsDetectedException( getClass(),
				"deleteScope",
				"Superclass",
				"SuperClass",
				"Relation",
				pkey );
		}

		// Delete is valid
		Map< CFBamScopePKey, CFBamScopeBuff > subdict;

		dictByPKey.remove( pkey );

		subdict = dictByTenantIdx.get( keyTenantIdx );
		subdict.remove( pkey );

	}
	public void deleteScopeByIdIdx( CFSecAuthorization Authorization,
		long argTenantId,
		long argId )
	{
		CFBamScopePKey key = schema.getFactoryScope().newPKey();
		key.setRequiredTenantId( argTenantId );
		key.setRequiredId( argId );
		deleteScopeByIdIdx( Authorization, key );
	}

	public void deleteScopeByIdIdx( CFSecAuthorization Authorization,
		CFBamScopePKey argKey )
	{
		final String S_ProcName = "deleteScopeByIdIdx";
		boolean anyNotNull = false;
		anyNotNull = true;
		anyNotNull = true;
		if( ! anyNotNull ) {
			return;
		}
		CFBamScopeBuff cur;
		LinkedList<CFBamScopeBuff> matchSet = new LinkedList<CFBamScopeBuff>();
		Iterator<CFBamScopeBuff> values = dictByPKey.values().iterator();
		while( values.hasNext() ) {
			cur = values.next();
			if( argKey.equals( cur ) ) {
				matchSet.add( cur );
			}
		}
		Iterator<CFBamScopeBuff> iterMatch = matchSet.iterator();
		while( iterMatch.hasNext() ) {
			cur = iterMatch.next();
			cur = schema.getTableScope().readDerivedByIdIdx( Authorization,
				cur.getRequiredTenantId(),
				cur.getRequiredId() );
			String subClassCode = cur.getClassCode();
			if( "a801".equals( subClassCode ) ) {
				schema.getTableScope().deleteScope( Authorization, cur );
			}
			else if( "a802".equals( subClassCode ) ) {
				schema.getTableSchemaDef().deleteSchemaDef( Authorization, (CFBamSchemaDefBuff)cur );
			}
			else if( "a804".equals( subClassCode ) ) {
				schema.getTableSchemaRef().deleteSchemaRef( Authorization, (CFBamSchemaRefBuff)cur );
			}
			else if( "a805".equals( subClassCode ) ) {
				schema.getTableServerMethod().deleteServerMethod( Authorization, (CFBamServerMethodBuff)cur );
			}
			else if( "a806".equals( subClassCode ) ) {
				schema.getTableServerObjFunc().deleteServerObjFunc( Authorization, (CFBamServerObjFuncBuff)cur );
			}
			else if( "a807".equals( subClassCode ) ) {
				schema.getTableServerProc().deleteServerProc( Authorization, (CFBamServerProcBuff)cur );
			}
			else if( "a837".equals( subClassCode ) ) {
				schema.getTableServerListFunc().deleteServerListFunc( Authorization, (CFBamServerListFuncBuff)cur );
			}
			else if( "a808".equals( subClassCode ) ) {
				schema.getTableTable().deleteTable( Authorization, (CFBamTableBuff)cur );
			}
			else if( "a810".equals( subClassCode ) ) {
				schema.getTableClearDep().deleteClearDep( Authorization, (CFBamClearDepBuff)cur );
			}
			else if( "a811".equals( subClassCode ) ) {
				schema.getTableClearSubDep1().deleteClearSubDep1( Authorization, (CFBamClearSubDep1Buff)cur );
			}
			else if( "a812".equals( subClassCode ) ) {
				schema.getTableClearSubDep2().deleteClearSubDep2( Authorization, (CFBamClearSubDep2Buff)cur );
			}
			else if( "a813".equals( subClassCode ) ) {
				schema.getTableClearSubDep3().deleteClearSubDep3( Authorization, (CFBamClearSubDep3Buff)cur );
			}
			else if( "a814".equals( subClassCode ) ) {
				schema.getTableClearTopDep().deleteClearTopDep( Authorization, (CFBamClearTopDepBuff)cur );
			}
			else if( "a817".equals( subClassCode ) ) {
				schema.getTableDelDep().deleteDelDep( Authorization, (CFBamDelDepBuff)cur );
			}
			else if( "a818".equals( subClassCode ) ) {
				schema.getTableDelSubDep1().deleteDelSubDep1( Authorization, (CFBamDelSubDep1Buff)cur );
			}
			else if( "a819".equals( subClassCode ) ) {
				schema.getTableDelSubDep2().deleteDelSubDep2( Authorization, (CFBamDelSubDep2Buff)cur );
			}
			else if( "a81a".equals( subClassCode ) ) {
				schema.getTableDelSubDep3().deleteDelSubDep3( Authorization, (CFBamDelSubDep3Buff)cur );
			}
			else if( "a81b".equals( subClassCode ) ) {
				schema.getTableDelTopDep().deleteDelTopDep( Authorization, (CFBamDelTopDepBuff)cur );
			}
			else if( "a821".equals( subClassCode ) ) {
				schema.getTableIndex().deleteIndex( Authorization, (CFBamIndexBuff)cur );
			}
			else if( "a830".equals( subClassCode ) ) {
				schema.getTablePopDep().deletePopDep( Authorization, (CFBamPopDepBuff)cur );
			}
			else if( "a831".equals( subClassCode ) ) {
				schema.getTablePopSubDep1().deletePopSubDep1( Authorization, (CFBamPopSubDep1Buff)cur );
			}
			else if( "a832".equals( subClassCode ) ) {
				schema.getTablePopSubDep2().deletePopSubDep2( Authorization, (CFBamPopSubDep2Buff)cur );
			}
			else if( "a833".equals( subClassCode ) ) {
				schema.getTablePopSubDep3().deletePopSubDep3( Authorization, (CFBamPopSubDep3Buff)cur );
			}
			else if( "a834".equals( subClassCode ) ) {
				schema.getTablePopTopDep().deletePopTopDep( Authorization, (CFBamPopTopDepBuff)cur );
			}
			else if( "a835".equals( subClassCode ) ) {
				schema.getTableRelation().deleteRelation( Authorization, (CFBamRelationBuff)cur );
			}
			else {
				throw new CFLibUnsupportedClassException( getClass(),
					S_ProcName,
					"subClassCode",
					cur,
					"Instance of or subclass of Scope must not be \"" + subClassCode + "\"" );
			}
		}
	}

	public void deleteScopeByTenantIdx( CFSecAuthorization Authorization,
		long argTenantId )
	{
		CFBamScopeByTenantIdxKey key = schema.getFactoryScope().newTenantIdxKey();
		key.setRequiredTenantId( argTenantId );
		deleteScopeByTenantIdx( Authorization, key );
	}

	public void deleteScopeByTenantIdx( CFSecAuthorization Authorization,
		CFBamScopeByTenantIdxKey argKey )
	{
		final String S_ProcName = "deleteScopeByTenantIdx";
		CFBamScopeBuff cur;
		boolean anyNotNull = false;
		anyNotNull = true;
		if( ! anyNotNull ) {
			return;
		}
		LinkedList<CFBamScopeBuff> matchSet = new LinkedList<CFBamScopeBuff>();
		Iterator<CFBamScopeBuff> values = dictByPKey.values().iterator();
		while( values.hasNext() ) {
			cur = values.next();
			if( argKey.equals( cur ) ) {
				matchSet.add( cur );
			}
		}
		Iterator<CFBamScopeBuff> iterMatch = matchSet.iterator();
		while( iterMatch.hasNext() ) {
			cur = iterMatch.next();
			cur = schema.getTableScope().readDerivedByIdIdx( Authorization,
				cur.getRequiredTenantId(),
				cur.getRequiredId() );
			String subClassCode = cur.getClassCode();
			if( "a801".equals( subClassCode ) ) {
				schema.getTableScope().deleteScope( Authorization, cur );
			}
			else if( "a802".equals( subClassCode ) ) {
				schema.getTableSchemaDef().deleteSchemaDef( Authorization, (CFBamSchemaDefBuff)cur );
			}
			else if( "a804".equals( subClassCode ) ) {
				schema.getTableSchemaRef().deleteSchemaRef( Authorization, (CFBamSchemaRefBuff)cur );
			}
			else if( "a805".equals( subClassCode ) ) {
				schema.getTableServerMethod().deleteServerMethod( Authorization, (CFBamServerMethodBuff)cur );
			}
			else if( "a806".equals( subClassCode ) ) {
				schema.getTableServerObjFunc().deleteServerObjFunc( Authorization, (CFBamServerObjFuncBuff)cur );
			}
			else if( "a807".equals( subClassCode ) ) {
				schema.getTableServerProc().deleteServerProc( Authorization, (CFBamServerProcBuff)cur );
			}
			else if( "a837".equals( subClassCode ) ) {
				schema.getTableServerListFunc().deleteServerListFunc( Authorization, (CFBamServerListFuncBuff)cur );
			}
			else if( "a808".equals( subClassCode ) ) {
				schema.getTableTable().deleteTable( Authorization, (CFBamTableBuff)cur );
			}
			else if( "a810".equals( subClassCode ) ) {
				schema.getTableClearDep().deleteClearDep( Authorization, (CFBamClearDepBuff)cur );
			}
			else if( "a811".equals( subClassCode ) ) {
				schema.getTableClearSubDep1().deleteClearSubDep1( Authorization, (CFBamClearSubDep1Buff)cur );
			}
			else if( "a812".equals( subClassCode ) ) {
				schema.getTableClearSubDep2().deleteClearSubDep2( Authorization, (CFBamClearSubDep2Buff)cur );
			}
			else if( "a813".equals( subClassCode ) ) {
				schema.getTableClearSubDep3().deleteClearSubDep3( Authorization, (CFBamClearSubDep3Buff)cur );
			}
			else if( "a814".equals( subClassCode ) ) {
				schema.getTableClearTopDep().deleteClearTopDep( Authorization, (CFBamClearTopDepBuff)cur );
			}
			else if( "a817".equals( subClassCode ) ) {
				schema.getTableDelDep().deleteDelDep( Authorization, (CFBamDelDepBuff)cur );
			}
			else if( "a818".equals( subClassCode ) ) {
				schema.getTableDelSubDep1().deleteDelSubDep1( Authorization, (CFBamDelSubDep1Buff)cur );
			}
			else if( "a819".equals( subClassCode ) ) {
				schema.getTableDelSubDep2().deleteDelSubDep2( Authorization, (CFBamDelSubDep2Buff)cur );
			}
			else if( "a81a".equals( subClassCode ) ) {
				schema.getTableDelSubDep3().deleteDelSubDep3( Authorization, (CFBamDelSubDep3Buff)cur );
			}
			else if( "a81b".equals( subClassCode ) ) {
				schema.getTableDelTopDep().deleteDelTopDep( Authorization, (CFBamDelTopDepBuff)cur );
			}
			else if( "a821".equals( subClassCode ) ) {
				schema.getTableIndex().deleteIndex( Authorization, (CFBamIndexBuff)cur );
			}
			else if( "a830".equals( subClassCode ) ) {
				schema.getTablePopDep().deletePopDep( Authorization, (CFBamPopDepBuff)cur );
			}
			else if( "a831".equals( subClassCode ) ) {
				schema.getTablePopSubDep1().deletePopSubDep1( Authorization, (CFBamPopSubDep1Buff)cur );
			}
			else if( "a832".equals( subClassCode ) ) {
				schema.getTablePopSubDep2().deletePopSubDep2( Authorization, (CFBamPopSubDep2Buff)cur );
			}
			else if( "a833".equals( subClassCode ) ) {
				schema.getTablePopSubDep3().deletePopSubDep3( Authorization, (CFBamPopSubDep3Buff)cur );
			}
			else if( "a834".equals( subClassCode ) ) {
				schema.getTablePopTopDep().deletePopTopDep( Authorization, (CFBamPopTopDepBuff)cur );
			}
			else if( "a835".equals( subClassCode ) ) {
				schema.getTableRelation().deleteRelation( Authorization, (CFBamRelationBuff)cur );
			}
			else {
				throw new CFLibUnsupportedClassException( getClass(),
					S_ProcName,
					"subClassCode",
					cur,
					"Instance of or subclass of Scope must not be \"" + subClassCode + "\"" );
			}
		}
	}

	public void releasePreparedStatements() {
	}
}
